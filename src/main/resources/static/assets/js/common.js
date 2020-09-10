 var common={
	init:function(obj)
	{
		return obj
	},serializeObject:function(form){
      var o = {};
        var a = form.serializeArray();
        $.each(a, function() {
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o
    },confirm : function(title, comment, type){
		var innerHTML = '';
		innerHTML += '<form name="formConfirm" id="formConfirm">';
		innerHTML += '<input type="hidden" name="title" value="" />';
		innerHTML += '<input type="hidden" name="comment" value="" />';
		innerHTML += '<input type="hidden" name="type" value="" />';
		innerHTML += '</form>';
		$("body").append(innerHTML);

		var myForm = document.formConfirm;
		var popupX = window.screenLeft+(((window.outerWidth)*0.5)-300);
	 	var popupY = window.screenTop+(((window.outerHeight)*0.5)-77);
		var option = 'width=546, height=157, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
		window.open('', "Confirm", option);

		myForm.action 				= "/Common/Confirm";
		myForm.method 				= "post";
		myForm.target 				= "Confirm";
		myForm.title.value 			= title;
		myForm.comment.value 		= comment;
		myForm.type.value 			= type;
		myForm.submit();
	},alert : function(title, comment){
		 var innerHTML = '';
		 innerHTML += '<form name="formAlert" id="formAlert">';
		 innerHTML += '<input type="hidden" name="title" value="" />';
		 innerHTML += '<input type="hidden" name="comment" value="" />';
		 innerHTML += '</form>';
		 $("body").append(innerHTML);

		 var myForm = document.formAlert;
		 var popupX = window.screenLeft+(((window.outerWidth)*0.5)-273);
		 var popupY = window.screenTop+(((window.outerHeight)*0.5)-77);
		 var option = 'width=546, height=157, left='+popupX+', top='+popupY+', resizable=no, scrollbars=no, status=no;';
		 window.open('', "Alert", option);

		 myForm.action 				= "/Common/Alert";
		 myForm.method 				= "post";
		 myForm.target 				= "Alert";
		 myForm.title.value 		= title;
		 myForm.comment.value 		= comment;
		 myForm.submit();
	 },onlyNumber(event){
	    event = event || window.event;
	    var keyID = (event.which) ? event.which : event.keyCode;
	    if ( (keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 || keyID == 9)
	        return;
	    else
	        return false;
	}
}
 
$(document).ready(function(){


	/* Input 숫자만 .numberOnly */
//	$(".numberOnly").on("keyup", function() {
//	    $(this).val($(this).val().replace(/[^0-9]/g,""));
//	});

	$( document ).on( "keyup", ".numberOnly", function( e ) {
		$(this).val($(this).val().replace(/[^0-9]/g,""));
	});
	/* Input 숫자만 */

});