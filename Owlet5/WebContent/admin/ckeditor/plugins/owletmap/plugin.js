CKEDITOR.plugins.add( 'owletmap', {
    icons: 'images',
    init: function( editor ) {
        editor.addCommand( 'insertImage' , {
        	exec: function( editor ) {
        		var avaliWidth = screen.availWidth;
        		var avaliHelight = screen.availHeight;
        		(window.open('/adder/photoUploader.do','win','width=884,height=671,toolbar=0,scrollbars=0,resizable=0')).focus();
        	}
        });
        editor.ui.addButton( 'Images', {
            label: 'Insert Image Map',
            command: 'insertImage',
            toolbar: 'owlet'
        });
    }
});