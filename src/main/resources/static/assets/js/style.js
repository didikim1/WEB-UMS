$(document).ready(function(){
	//GNB
	$('.gnb > li ').bind('mouseover keyup', function() {
		$('#header').addClass("hover");
		$('.gnb > li ul ').css("display","none");
		$(this).find("ul").css("display","block");
	});
	$('.gnb > li ').bind('mouseout keydown', function() {
		var chkOn = $('.gnb > li').hasClass("on");
		$('.gnb > li ul ').css("display","none");
		$('#header').removeClass("hover");
		if(chkOn){
			$('#header').addClass("on");
			$('.gnb > li.on ul').css("display","block");
		}
	});

	//popup
	$('.btnPop').click(function(){
	        var $href = $(this).attr('href');
//	        console.log($href)
	        $("#layer1").find("img").attr("src", $href);
	        layer_popup("#layer1");

	        console.log("1")

//	        layer_popup($href);

			return false;
    });

	//tabs
	$('.tabsHead a').click(function(){
		var tabs = $(this).closest(".tabs");
		var myIdx = $(this).closest("li").index();

		tabs.find(".tabsHead li").removeClass("on");
		tabs.find(".tabsCont").removeClass("on");
		$(this).closest("li").addClass("on");
		tabs.find(".tabsCont:eq("+myIdx+")").addClass("on");
		return false;
    });

	//slider
	$('.mainSlider').filter(function(){
		$(this).slick({
			dots: true,
			infinite: false,
			speed: 300,
			slidesToShow: 1,
			centerMode: false,
			slidesToScroll: 1
		});
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
