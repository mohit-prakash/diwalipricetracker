package com.mps.utility;
	
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mps.model.DiwaliPriceModel;
	
public interface ExtractPrice {
		public static DiwaliPriceModel findPrice(String args) throws IOException  {
			
			DiwaliPriceModel diwaliPriceModel=new DiwaliPriceModel();
			XSSFWorkbook workbook1=null;
			try {
					FileInputStream fis1 = new FileInputStream("src/main/java/com/mps/utility/DiwaliPriceList.xlsx");	 
					workbook1 = new XSSFWorkbook(fis1);	 
					XSSFSheet sheet1 = workbook1.getSheetAt(0);
					
					String input=args;
					input=input.toUpperCase();
					
					for(int i=1;i<=sheet1.getLastRowNum();i++)
					{
						String model=""+sheet1.getRow(i).getCell(0);
						model=model.trim();
						if(model.contains(input))
						{
							diwaliPriceModel.setModelName(model);
							diwaliPriceModel.setMrp(""+sheet1.getRow(i).getCell(1));
							diwaliPriceModel.setDp(sheet1.getRow(i).getCell(2)+"");
							diwaliPriceModel.setSrp(sheet1.getRow(i).getCell(3)+"");
							return diwaliPriceModel;
						}
					}	
					return new DiwaliPriceModel("Model Not Found!!","","","");
			}
			catch(IOException ioe)
			{
					System.out.println(ioe.getMessage());
					return new DiwaliPriceModel("File not found","","","");
			}
			finally
			{
				workbook1.close();
			}
		}

	}
