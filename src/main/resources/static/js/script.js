$(document).ready(function(){
    $(".section_1_text").hide()

    $(".button-1").click(function(){
        $(".button-1").css({"background-color":"#00cc66","color":"#f7f7f7"});
        $(".button-2,.button-3").css({"background-color":"#f7f7f7","color":"#222121"});
        $("#b").hide();
        $("#c").hide();
        $("#a").show();});

    $(".button-2").click(function(){
        $(".button-2").css({"background-color":"#00cc66","color":"#f7f7f7"});
        $(".button-1,.button-3").css({"background-color":"#f7f7f7","color":"#222121"});
        $("#a").hide();
        $("#c").hide();
        $("#b").show();});

    $(".button-3").click(function(){
        $(".button-3").css({"background-color":"#00cc66","color":"#f7f7f7"});
        $(".button-1,.button-2").css({"background-color":"#f7f7f7","color":"#222121"});
        $("#b").hide();
        $("#a").hide();
        $("#c").show();});


});