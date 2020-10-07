$(document).ready(function(){
    $('.tTip').tooltip();
    $('.signup_form .logo_add_btn').click(function(){
        var $elem = $(this);
        var $file_choose = $('.signup_form input[name=logo]');
       $file_choose.click().change(function(){
         if (this.files && this.files[0]) {		
			var reader = new FileReader();
			reader.onload = function (e) {
				$elem.css({
				"background"		:	"url('"+e.target.result+"')",
				"background-size"	:	"cover"
				});
			}
			reader.readAsDataURL(this.files[0]);
		}
       });
    });
     $('.signup_form input[type=submit]').click(function(e){
         var $form = $('.signup_form');
         if(!$form.find('input[name=logo]').val()){
             alert("Please Upload Logo !");
             e.preventDefault();
         }else{
         var $modal = $('#signupCategoryChooseModal');
         $modal.modal('show');
         $modal.find('.signup_option_item').click(function(){
             var selected_option = $(this).attr('data-value');
             alert(selected_option);
             $form.find('input[name=account_type]').val(selected_option);
            $form.submit();  
         });
     }
         e.preventDefault();
     });
    
});