function image_edit() {
	alert("image_edit()");
}
function submit_edit() {
	document.info_form.submit();
}
function cancle_edit() {
	window.close();
}

window.onload = function() {

	if (window.File && window.FileList && window.FileReader) {
		var filesInput = document.getElementById("files");

		filesInput.addEventListener("change", function(event) {

			var files = event.target.files;

			var output = document.getElementById("edit_image");
			var inner = "";

			for (var i = 0; i < files.length; i++) {
				var file = files[i];
				var div = document.createElement("div");

				if (!file.type.match('image')) {
					alert("해당파일은 이미지 파일이 아닙니다");
					location.reload(true);
					break;
				}

				var picReader = new FileReader();

				picReader.addEventListener("load", function(event) {

					var picFile = event.target;
					var img = document.createElement("img");
					img.src = picFile.result;
					img.className = "thumbnail";

					output.insertBefore(img, null);

				});
				
				picReader.readAsDataURL(file);
			}

		});
	} else {
		console.log("Your browser does not support File API");
	}
}

$(window).load(function() {
	var intervalFunc = function() {
		$('#file-name').html($('#file').val());
	};
	$('#browse-click').on('click', function() { // use .live() for older versions of jQuery
		$('#files').click()
		setInterval(intervalFunc, 1);
		return false;
	});
});