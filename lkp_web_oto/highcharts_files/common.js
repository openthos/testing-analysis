$(function(){

	var current = location.pathname.split("/")[1];
	if(current == null || current == "index.php" || current=="") current = "index";
	if(current==="api" || current === "index") { 
		switchService(false); 
	} else {
		switchService(true);
	}

	if(current === 'docs' ) current = 'api';
	$("#navigater").find("li[index=\""+current+"\"]").addClass("active");

	$.scrollUp({
	      scrollName: 'scrollUp',
	      scrollDistance: 300,
	      scrollFrom: 'top',
	      scrollSpeed: 100,
	      easingType: 'linear',
	      animation: 'fade',
	      animationInSpeed: 200,
	      animationOutSpeed: 200, 
	      scrollTitle: '返回顶部',
	      scrollImg: true,
	      activeOverlay: false,
	      zIndex: 2147483647
	});


	function switchService(flag) {
        $(".online-btn").css("display", flag ? 'none' : 'block');
        $('.online-content').css('display', flag ? 'block' : 'none');
    }

    $(".online-btn").click(function() {
        switchService(true);
    });

    $('.online-close').click(function() {
        switchService(false);
    });


	// baidu Share
	window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"1","bdMiniList":false,"bdPic":"","bdStyle":"0","bdSize":"24"},"share":{}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];
	// baidu Tongji
	var _hmt = _hmt || [];
	(function() {
	  var hm = document.createElement("script");
	  hm.src = "//hm.baidu.com/hm.js?e561159f9fc1f6dcc181c90202d3353a";
	  var s = document.getElementsByTagName("script")[0]; 
	  s.parentNode.insertBefore(hm, s);
	})();
});

/* Other Functions, such as 
function aa() {
	do some thing;
}
*/

/****** License functions *****/
function showH(target) {
	$(".li_type").find("li.cur").removeClass("cur");
	$(".li_type").find("li[index='"+target+"']").addClass("cur");
	$(".h").hide();
	$("."+target).show();
}

function step(target,left) {
	$(".li_step").find(".li_ico").css({"left":left});
	$(".li_step").find(".step").hide();
	$(".li_step").find("."+target).show();
}


function AutoScroll(obj){ 
	$(obj).find("ul:first").animate({ 
		marginTop:"-18px" 
		},500,function(){ 
		$(this).css({marginTop:"0px"}).find("li:first").appendTo(this); 
	}); 
} 
