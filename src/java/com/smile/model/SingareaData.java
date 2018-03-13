package com.smile.model;

public class SingareaData {

		private String  area_ty = new String("");
		private String  area_na = new String("");
        private String  area_en = new String("");

		public SingareaData() {
			initiateSingarearecord();
		}
		
		public void initiateSingarearecord() {
            setArea_ty("");
            setArea_na("");
            setArea_en("");
		}
		
		public void setArea_ty(String area_ty) {
			this.area_ty = area_ty;
		}
		public String getArea_ty() {
			return this.area_ty;
		}
		public void setArea_na(String area_na) {
			this.area_na = area_na;
		}
		public String getArea_na() {
			return this.area_na;
		}
        public void setArea_en(String area_en) {
            this.area_en = area_en;
        }
        public String getArea_en() {
            return this.area_en;
        }
	}
