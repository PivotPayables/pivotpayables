package com.pivotpayables.concurplatform;

public class ItemtoAllocation {
	public static Allocation createAllocation(Itemization item){
		/*
		 * This method creates an allocation from a itemization.  This handles the use case
		 * when the itemization's ApprovedAmount = 0, which means there will be no allocation.  PivotPrime
		 * still creates a Charge document for this use case.  
		 * 
		 * This method assumes that the field contexts for the Allocations form are in the same custom fields
		 * as the ExpenseEntry form.  This assumption may not be true for a few, Professional Customers.  So, a to do is
		 * to design a solution for this use case.
		 */
		
		// TODO design a solution for converting an item to an allocation when the field contexts are different between ExpenseEntry and Allocations
		Allocation allocation = new Allocation();
		allocation.setPercentage("100");
		if (item.getCustom1() != null){
			allocation.setCustom1(item.getCustom1());
		}
		if (item.getCustom2() != null){
			allocation.setCustom2(item.getCustom2());
		}
		if (item.getCustom3() != null){
			allocation.setCustom3(item.getCustom3());
		}
		if (item.getCustom4() != null){
			allocation.setCustom4(item.getCustom4());
		}
		if (item.getCustom5() != null){
			allocation.setCustom5(item.getCustom5());
		}
		if (item.getCustom6() != null){
			allocation.setCustom6(item.getCustom6());
		}
		if (item.getCustom7() != null){
			allocation.setCustom7(item.getCustom7());
		}
		if (item.getCustom8() != null){
			allocation.setCustom8(item.getCustom8());
		}
		if (item.getCustom9() != null){
			allocation.setCustom9(item.getCustom9());
		}
		if (item.getCustom10() != null){
			allocation.setCustom10(item.getCustom10());
		}
		if (item.getCustom11() != null){
			allocation.setCustom11(item.getCustom11());
		}
		if (item.getCustom12() != null){
			allocation.setCustom12(item.getCustom12());
		}
		if (item.getCustom13() != null){
			allocation.setCustom13(item.getCustom13());
		}
		if (item.getCustom14() != null){
			allocation.setCustom14(item.getCustom14());
		}
		if (item.getCustom15() != null){
			allocation.setCustom15(item.getCustom15());
		}
		if (item.getCustom16() != null){
			allocation.setCustom16(item.getCustom16());
		}
		if (item.getCustom17() != null){
			allocation.setCustom17(item.getCustom17());
		}
		if (item.getCustom18() != null){
			allocation.setCustom18(item.getCustom18());
		}
		if (item.getCustom19() != null){
			allocation.setCustom19(item.getCustom9());
		}
		if (item.getCustom20() != null){
			allocation.setCustom20(item.getCustom20());
		}

		return allocation;
	}
}
