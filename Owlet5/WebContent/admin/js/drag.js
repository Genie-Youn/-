$(function() {
	$("ul, li").disableSelection();

	$("#sortable").sortable({
		revert : true
	});

	$("#sortable2").sortable({
		revert : true
	});
});
function div_OnOff(v, id) {
	if (v == "1") {
		document.getElementById("vertical").style.display = "";
		document.getElementById("horizontal").style.display = "none";
	} else if (v == "2") {
		document.getElementById("vertical").style.display = "none";
		document.getElementById("horizontal").style.display = "";
	}
}