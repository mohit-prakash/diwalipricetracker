package com.mps.utility;
	
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
	
public class ExtractPrice {
		public static String[] findPrice(String args) throws IOException  {
			
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
							return new String[] {model,sheet1.getRow(i).getCell(1)+"",sheet1.getRow(i).getCell(2)+"",sheet1.getRow(i).getCell(3)+""};
						}
					}	
					return new String[] {"","Model not found!!","",""};
			}
			catch(IOException ioe)
			{
					System.out.println(ioe.getMessage());
					return new String[]{"","File not found","",""};
			}
			finally
			{
				workbook1.close();
			}
		}

	}
