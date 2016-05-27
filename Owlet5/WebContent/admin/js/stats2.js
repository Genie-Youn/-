window.onload = function() {
	var r = Raphael("holder"), data1 = [ [ 10, 20, 13, 32, 5, 1, 2, 10 ],
			[ 10, 2, 1, 5, 32, 13, 20, 55 ], [ 12, 20, 30 ] ], txtattr = {
		font : "12px 'Fontin Sans', Fontin-Sans, sans-serif"
	};

	r.text(480, 10, "Multiline Series Chart").attr(txtattr);
	r.barchart(250, 10, 500, 250, data1);
	
};