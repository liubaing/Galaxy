$(document).ready(function(e) {
    $("#apps_contact,#apps_content,#apps_download,#apps_faq,#apps_home").click(function(e) {
		var classname = $(this).attr("id");
		if($("."+classname).css('display') == "none"){
			$(".apps_nav div").css('background-color',"transparent");
			$("div#apps_content").each(function(index, element) {
				if($(this).css('display') == "block"){
					$(this).fadeOut(300);
				}
			});
			$("."+classname).delay(300).fadeIn(300);
			$(".apps_nav div#"+classname).removeClass("div_hover").css('background-color',"#6c6c6c");
		}
    });
	$(".apps_nav div").hover(function(e) {
        var classname = $(this).attr("id");
		if($("."+classname).css('display') == "none"){
			$(this).addClass("div_hover");
		}
    }, function(e){
		$(this).removeClass("div_hover");
	});
});