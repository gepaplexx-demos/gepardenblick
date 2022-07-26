function scrollDownN(){
    $(window).scroll(function() {
        $('html, body').animate({
            scrollTop: $("#footer").offset().top
        }, 2000);
    });
}