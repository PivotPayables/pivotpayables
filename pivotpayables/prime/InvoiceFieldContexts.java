package com.pivotpayables.prime;

public class InvoiceFieldContexts {
	public static FieldContext processFormType(FieldContext context){
		switch (context.getFormType()){
			case "PaymentRequest":
				context.setFormType("ExpenseEntry");// convert "PaymentRequest" to "ExpenseEntry" so that the CreateChargeDocs class can find the correct custom field
				return context;
			case "InvoiceAllocation":
				context.setFormType("Allocations");// convert "InvoiceAllocations" to "Allocations" so that the CreateChargeDocs class can find the correct custom field
				return context;
		

		}
		return context;
		
	}		

}
