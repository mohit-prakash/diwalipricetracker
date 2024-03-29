package com.mps.utility;
	
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.mps.model.DiwaliPriceModel;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExtractPrice {
	private final String filePath="DiwaliPriceList.xlsx";
	public DiwaliPriceModel findPrice(String args) throws IOException {

		DiwaliPriceModel diwaliPriceModel = new DiwaliPriceModel();
		XSSFWorkbook workbook1 = null;
		try {
			FileInputStream fis1 = new FileInputStream(filePath);
			workbook1 = new XSSFWorkbook(fis1);
			XSSFSheet sheet1 = workbook1.getSheetAt(0);

			String input = args;
			input = input.toUpperCase().trim();

			for (int i = 1; i <= sheet1.getLastRowNum(); i++) {
				String model = "" + sheet1.getRow(i).getCell(0);
				model = model.toUpperCase().trim();
				if (model.contains(input)) {
					diwaliPriceModel.setModelName(model);
					diwaliPriceModel.setMrp("" + sheet1.getRow(i).getCell(1));
					diwaliPriceModel.setDp(sheet1.getRow(i).getCell(2) + "");
					diwaliPriceModel.setSrp(sheet1.getRow(i).getCell(3) + "");
					return diwaliPriceModel;
				}
			}
			return new DiwaliPriceModel("Model Not Found!!", "", "", "");
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			return new DiwaliPriceModel("File not found", "", "", "");
		} finally {
			workbook1.close();
		}
	}

	public List<DiwaliPriceModel> getWholeList() throws IOException {
		List<DiwaliPriceModel> list = new ArrayList<>();
		XSSFWorkbook workbook1 = null;
		try {
			FileInputStream fis1 = new FileInputStream(filePath);
			workbook1 = new XSSFWorkbook(fis1);
			XSSFSheet sheet1 = workbook1.getSheetAt(0);
			int x= sheet1.getLastRowNum();
			for (int i = 1; i <= sheet1.getLastRowNum(); i++) {
				DiwaliPriceModel diwaliPriceModel = DiwaliPriceModel.builder().modelName(sheet1.getRow(i).getCell(0).toString())
						.mrp(sheet1.getRow(i).getCell(1).toString())
						.dp(sheet1.getRow(i).getCell(2).toString())
						.srp(sheet1.getRow(i).getCell(3).toString()).build();
				list.add(diwaliPriceModel);
			}
		} catch (IOException ioe) {
			log.info("Something Wrong" + ioe.getMessage());
		} finally {
			workbook1.close();
		}
		return list;
	}

	public boolean updatePrice(DiwaliPriceModel diwaliPriceModel) throws IOException {
		XSSFWorkbook workbook1 = null;
		boolean flag=false;
		try {
			FileInputStream fis1 = new FileInputStream(filePath);
			workbook1 = new XSSFWorkbook(fis1);
			XSSFSheet sheet1 = workbook1.getSheetAt(0);
			for (int i = 1; i <= sheet1.getLastRowNum(); i++) {
				String model = "" + sheet1.getRow(i).getCell(0);
				model = model.trim();
				if (diwaliPriceModel.getModelName().equalsIgnoreCase(model)) {
					sheet1.getRow(i).getCell(1).setCellValue(diwaliPriceModel.getMrp());
					sheet1.getRow(i).getCell(2).setCellValue(diwaliPriceModel.getDp());
					sheet1.getRow(i).getCell(3).setCellValue(diwaliPriceModel.getSrp());
					flag=true;
					FileOutputStream fos1 = new FileOutputStream(filePath);
					workbook1.write(fos1);
					fos1.close();
					break;
				}
			}
		} catch (IOException ioe) {
			log.info("Something wrong while updating "+ioe.getMessage());
		} finally {
			workbook1.close();
		}
		return flag;
	}
	public boolean removeModel(String modelName) throws IOException {
		XSSFWorkbook workbook1 = null;
		boolean flag=false;
		try {
			FileInputStream fis1 = new FileInputStream(filePath);
			workbook1 = new XSSFWorkbook(fis1);
			XSSFSheet sheet1 = workbook1.getSheetAt(0);
			for (int i = 1; i <= sheet1.getLastRowNum(); i++) {
				String model = "" + sheet1.getRow(i).getCell(0);
				model = model.trim();
				if (model.contains(modelName)) {
					int rowIndex = sheet1.getRow(i).getRowNum();
					int lastRowNum = sheet1.getLastRowNum();
					sheet1.removeRow(sheet1.getRow(i));
					if(rowIndex>=0&&rowIndex<lastRowNum) {
						sheet1.shiftRows(rowIndex + 1, lastRowNum, -1);
					}
					flag=true;
					FileOutputStream fos1 = new FileOutputStream(filePath);
					workbook1.write(fos1);
					fos1.close();
					break;
				}
			}
		} catch (IOException ioe) {
			log.info("Something wrong while removing "+ioe.getMessage());
		} finally {
			workbook1.close();
		}
		return flag;
	}

	public boolean addModel(DiwaliPriceModel diwaliPriceModel) throws IOException {
		XSSFWorkbook workbook1 = null;
		boolean flag=false;
		try {
			FileInputStream fis1 = new FileInputStream(filePath);
			workbook1 = new XSSFWorkbook(fis1);
			XSSFSheet sheet1 = workbook1.getSheetAt(0);
			int lastRowNum = sheet1.getLastRowNum();
			int rowCount=1;
			sheet1.shiftRows(1, lastRowNum, rowCount, true, true);
			sheet1.createRow(rowCount);
			sheet1.getRow(1).createCell(0).setCellValue(diwaliPriceModel.getModelName());
			sheet1.getRow(1).createCell(1).setCellValue(diwaliPriceModel.getMrp());
			sheet1.getRow(1).createCell(2).setCellValue(diwaliPriceModel.getDp());
			sheet1.getRow(1).createCell(3).setCellValue(diwaliPriceModel.getSrp());
			sheet1.getRow(1).createCell(4).setCellValue(getType(diwaliPriceModel.getModelName()));
			FileOutputStream fos1 = new FileOutputStream(filePath);
			workbook1.write(fos1);
			flag=true;
			fos1.close();
		} catch (IOException ioe) {
			log.info("Something wrong while updating "+ioe.getMessage());
		} finally {
			workbook1.close();
		}
		return flag;
	}
	public List<DiwaliPriceModel> findModelByRange(String startPrice, String endPrice,String category) throws IOException {
		List<DiwaliPriceModel> list = searchByCategory(category);
		List<DiwaliPriceModel> modelList = list.stream().filter((model) -> {
			String srpStr = model.getSrp();
			srpStr = srpStr.substring(0, srpStr.indexOf('.'));
			long srp = Long.parseLong(srpStr);
			long sp = Long.parseLong(startPrice);
			long ep = Long.parseLong(endPrice);
			if (srp >= sp && srp <= ep) {
				return true;
			} else {
				return false;
			}
		}).collect(Collectors.toList());
		return modelList;
	}
	public List<DiwaliPriceModel> findModelByRange(String startPrice, String endPrice) throws IOException {
		List<DiwaliPriceModel> list = new ArrayList<>();
		XSSFWorkbook workbook1 = null;
		long sp=Long.parseLong(startPrice);
		long ep=Long.parseLong(endPrice);
		try {
			FileInputStream fis1 = new FileInputStream(filePath);
			workbook1 = new XSSFWorkbook(fis1);
			XSSFSheet sheet1 = workbook1.getSheetAt(0);
			for (int i = 1; i <= sheet1.getLastRowNum(); i++) {
				String srpStr = sheet1.getRow(i).getCell(3).toString();
				srpStr=srpStr.substring(0,srpStr.indexOf('.'));
				long srp=Long.parseLong(srpStr);
				if (srp>=sp && srp<=ep){
					DiwaliPriceModel diwaliPriceModel = DiwaliPriceModel.builder().modelName(sheet1.getRow(i).getCell(0).toString())
							.mrp(sheet1.getRow(i).getCell(1).toString())
							.dp(sheet1.getRow(i).getCell(2).toString())
							.srp(sheet1.getRow(i).getCell(3).toString()).build();
					list.add(diwaliPriceModel);
				}
			}
		} catch (IOException ioe) {
			log.info("Something Wrong" + ioe.getMessage());
		} finally {
			workbook1.close();
		}
		return list;
	}

	public List<DiwaliPriceModel> searchByCategory(String category) throws IOException {
		List<DiwaliPriceModel> list = new ArrayList<>();
		XSSFWorkbook workbook1 = null;
		try {
			FileInputStream fis1 = new FileInputStream(filePath);
			workbook1 = new XSSFWorkbook(fis1);
			XSSFSheet sheet1 = workbook1.getSheetAt(0);
			for (int i = 1; i <= sheet1.getLastRowNum(); i++) {
				String catFromFile = sheet1.getRow(i).getCell(4).toString();
				if (catFromFile.contains(category)){
					DiwaliPriceModel diwaliPriceModel = DiwaliPriceModel.builder().modelName(sheet1.getRow(i).getCell(0).toString())
							.mrp(sheet1.getRow(i).getCell(1).toString())
							.dp(sheet1.getRow(i).getCell(2).toString())
							.srp(sheet1.getRow(i).getCell(3).toString()).build();
					list.add(diwaliPriceModel);
				}
			}
		} catch (IOException ioe) {
			log.info("Something Wrong" + ioe.getMessage());
		} finally {
			workbook1.close();
		}
		return list;
	}

	private String getType(String modelName){
		if (modelName.startsWith("3")){
			return "WWM";
		} else if(modelName.startsWith("2") || modelName.startsWith("7")){
			return "WRR";
		} else if (modelName.startsWith("RR") || modelName.startsWith("RT")){
			return "SRR";
		} else if (modelName.startsWith("WA") || modelName.startsWith("WM")){
			return "SWM";
		} else if (modelName.startsWith("FH") || modelName.startsWith("P") || modelName.startsWith("T")){
			return "LWM";
		} else if (modelName.startsWith("GL")){
			return "LRR";
		} else if (modelName.startsWith("IFB")){
			return "IWM";
		} else {
			return "";
		}
	}

}
