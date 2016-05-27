(function() {

	var pw_length = 0;
	var pw_vaild = null;
	var pw_invaild = null;
	var pw_confirm_vaild = null;
	var pw_confirm_invaild = null;
	var pw_obj = null;
	var pw_confirm_obj = null;
	var next_obj = null;

	jQuery(function() {
		// console.log(jQuery('input[name=mail]'));
		pw_vaild = jQuery('div.passwd').find('div.icon-check');
		pw_invaild = jQuery('div.passwd').find('div.icon-x');

		pw_confirm_vaild = jQuery('div.passwdConfirm').find('div.icon-check');
		pw_confirm_invaild = jQuery('div.passwdConfirm').find('div.icon-x');

		pw_obj = jQuery('input[name=passwd]');
		pw_confirm_obj = jQuery('input[name=passwdConfirm]');

		pw_vaild.hide();
		pw_invaild.hide();
		pw_confirm_vaild.hide();
		pw_confirm_invaild.hide();

		next_obj = jQuery('#next');
		CanNext();

		function CanNext() {
			if (pw_length >= 4 && pw_length <= 15
					&& pw_obj.val() == pw_confirm_obj.val()) {
				next_obj.attr('disabled', true);
				next_obj.removeAttr('disabled');
			} else {
				next_obj.removeAttr('disabled');
				next_obj.attr('disabled', true);
			}
		}

		pw_obj.keyup(function() {
			pw_length = pw_obj.val().length;

			if (pw_length >= 4 && pw_length <= 15) {
				pw_invaild.hide();
				pw_vaild.show();
			} else {
				pw_vaild.hide();
				pw_invaild.show();
			}

			CanNext();
		});

		pw_confirm_obj.focusout(function() {
			if (pw_obj.val() == pw_confirm_obj.val()) {
				pw_confirm_invaild.hide();
				pw_confirm_vaild.show();
			} else {
				pw_confirm_vaild.hide();
				pw_confirm_invaild.show();
			}

			CanNext();
		});

		next_obj.click(function() {
			jQuery('body').css('overflow-y', 'hidden');
			$('#basicInfo').slideUp(function() {
				jQuery('body').css('overflow-y', 'auto');
			});
			$('#extendsInfo').show();
		});

		// 릴리즈 전에는 지울 것.
		if (false) {
			jQuery('body').css('overflow-y', 'hidden');
			$('#basicInfo').slideUp(function() {
				jQuery('body').css('overflow-y', 'auto');
			});
			$('#extendsInfo').show();
		}

	});
})();