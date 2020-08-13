$(document).ready(function(){
	//popup
	$('.btnPop').click(function(){
        var $href = $(this).attr('href');
        layer_popup($href);

		return false;
    });

	//layout 
	layoutHeight();
	$(window).resize(function() {
		layoutHeight();
	});
});

function layer_popup(el){
	var $el = $(el);
	var $scollCont = $el.find(".scrollCont");

	$(".popLayer").removeClass("on");
	$(".layerArea").addClass("on");
	$el.fadeIn(); 
	$("html, body").addClass("on");

 $('.btnClose').click(function(){  
	$(".popLayer").fadeOut();
	$("html, body").removeClass("on");
	$(".layerArea").removeClass("on");
	return false;
 });

};


function layer_auto(el){
 var $el = $(el);

	$(".layerArea").addClass("on");
	$el.fadeIn();
	$("html, body").addClass("on");

	$('.btnClose').click(function(){  
		$(".popLayer").fadeOut();
	$("html, body").removeClass("on");
		$(".layerArea").removeClass("on");
		return false;
	});
};


//layout
function layoutHeight($obj) {
	var windowH = $(window).height();
	var wrapH = $('#wrap').height();
	var headH = $('#header').outerHeight();
	var footerH = $('#footer').outerHeight();
	var bodyH = windowH -(headH+footerH)
	if(wrapH<=windowH){
		$('#contents').css("height",bodyH);
	}
}
