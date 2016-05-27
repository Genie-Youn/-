/**
 * ▲ 버튼 또는 ▼ 버튼을 눌렀을 때, 슬라이드 시키는 코드에요.
 */

$(document).ready(function(){
jQuery('.chart-widget .tools .slot-icon-down').click(function () {
        var el = jQuery(this).parents(".chart-widget").children(".slot-body");
        if (jQuery(this).hasClass("slot-icon-down")) {
            jQuery(this).removeClass("slot-icon-down").addClass("icon-chevron-up");
            el.slideUp(200);
        } else {
            jQuery(this).removeClass("icon-chevron-up").addClass("slot-icon-down");
            el.slideDown(200);
        }
    });

    jQuery('.chart-widget .tools .slot-icon-delete').click(function () {
        jQuery(this).parents(".chart-widget").parent().remove();
    });
    
}

);