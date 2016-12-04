package zakemi.solteq.Assignment.model;

public class ReportsModels {

	public static class MonthhySalary{
		
		private Integer year;
		private Integer month;
		private Double amount;
		
		public MonthhySalary(Integer year, Integer month, Double amount) {
			super();
			this.year = year;
			this.month = month;
			this.amount = amount;
		}
		
		public Integer getYear() {
			return year;
		}
		
		public Integer getMonth() {
			return month;
		}
		
		public Double getAmount() {
			return amount;
		}
		
	}
	
}
