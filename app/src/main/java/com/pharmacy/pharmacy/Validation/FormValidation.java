package com.pharmacy.pharmacy.Validation;


import android.text.Editable;
import android.widget.EditText;
public class FormValidation {

	/*public final static Pattern EMAIL_ADDRESS_PATTERN = Pattern
			.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
					+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
					+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");

	public static boolean checkEmail(String email) {
		return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
	}*/
    public boolean Is_Valid_EmailLogin(EditText edt) {
        if (edt.getText().toString() == null) {
           /* if (AppController.getInstance().getLanguage().equalsIgnoreCase("ar")){
                edt.setError("من فضلك قم بادخال البريد الإلكتروني");
            }
            else {*/
                edt.setError("من فضلك ادخل بريدك الإلكتروني");
            //}
           // edt.setBackgroundResource(R.drawable.underline_background_editview_red);
            return false;
        } else if (isEmailValid(edt.getText().toString()) == false) {
           /* if(AppController.getInstance().getLanguage().equalsIgnoreCase("ar")){
                edt.setError("من فضلك قم بإدخال بريد إلكتروني صحيح");
            }
            else {*/
                edt.setError("من فضلك ادخل بريد إلكتروني صحيح");
            //}
           // edt.setBackgroundResource(R.drawable.underline_background_editview_red);


            return false;
        } else {
           // edt.setBackgroundResource(R.drawable.login_underline);
            return true;
        }
    }
    public boolean Is_Valid_Email(EditText edt) {
        if (edt.getText().toString() == null) {
           /* if (AppController.getInstance().getLanguage().equalsIgnoreCase("ar")){
                edt.setError("من فضلك قم بادخال البريد الإلكتروني");
            }
            else {*/
                edt.setError("من فضلك ادخل ايميلك");
           // }
            //edt.setBackgroundResource(R.drawable.underline_background_editview_red);
            return false;
        } else if (isEmailValid(edt.getText().toString()) == false) {
            /*if(AppController.getInstance().getLanguage().equalsIgnoreCase("ar")){
                edt.setError("من فضلك قم بإدخال بريد إلكتروني صحيح");
            }
            else {*/
                edt.setError("Please Enter Valid Email");
          //  }
           // edt.setBackgroundResource(R.drawable.underline_background_editview_red);


            return false;
        } else {
            //edt.setBackgroundResource(R.drawable.underline_background_editview);
            return true;
        }
    }
    public boolean Is_Valid_Email_Toast(EditText edt) {
        if (edt.getText().toString() == null) {

            //edt.setBackgroundResource(R.drawable.underline_background_editview_red);
            return false;
        } else if (isEmailValid(edt.getText().toString()) == false) {

          //  edt.setBackgroundResource(R.drawable.underline_background_editview_red);


            return false;
        } else {
            //edt.setBackgroundResource(R.drawable.underline_profile);
            return true;
        }
    }
    public boolean IS_Contain_Space(EditText edt,String name){
        if (edt.getText().toString() == null) {
            edt.setError("Please Enter Your "+name);
            return false;
        }
// else

//        if (edt.getText().toString().contains(" ")) {
//            edt.setError("Please Remove Space from "+name);
//            return false;
//        }

        else {
            return true;
        }
    }
  public   boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public boolean Is_Valid_VIN(String vin){
        if(vin.length()==6){

            return true;
        }
        else {
            return false;
        }
    }
    public boolean Is_Valid_Password(EditText password){
        if(password.getText().toString().length()>=6||password.getText().toString()!=null||password.getText().toString().trim().length()>0){

            return true;
        }
        else {
            if(password.getText().toString().trim().length()==0||password.getText().toString()==null)
            {
                password.setError("من فضلك ادخل كلمة المرور");
            }
            else if (password.getText().toString().length()<6) {
                password.setError("كلمة المرور يجب ألا تقل عن 6");
            }
            return false;
        }
    }
   public boolean Is_Password_equal_Confirm_password(EditText password,EditText confirmPassword){
       if(confirmPassword.getText().toString().equals(password.getText().toString())){
           return true;
       }
       else{
           confirmPassword.setError("كلمات المرور غير متساوية");
           return false;
       }
   }
    public boolean Is_Valid_UserName(EditText edt){
       if(edt.getText().toString()==null){
           edt.setError("من فضلك ادخل اسم المستخدم");
           return false;
       }
       else if (edt.getText().toString().trim().length()==0){
           edt.setError("من فضلك ادخل اسم المستخدم");
           return false;
       }
       else {
           return true;
       }

    }
    public boolean Is_Valid_mobile(EditText edt){
        if(edt.getText().toString()==null){
            edt.setError("من فضلك ادخل الموبيل");
            return false;
        }
        else if (edt.getText().toString().trim().length()==0){
            edt.setError("من فضلك ادخل الموبيل");
            return false;
        }
        else {
            return true;
        }

    }
    public boolean Is_Valid_address(EditText edt){
        if(edt.getText().toString()==null){
            edt.setError("من فضلك ادخل العنوان");
            return false;
        }
        else if (edt.getText().toString().trim().length()==0){
            edt.setError("من فضلك ادخل العنوان");
            return false;
        }
        else {
            return true;
        }

    }
}
