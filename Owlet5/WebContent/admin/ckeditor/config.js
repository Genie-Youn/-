/**
 * @license Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	
	config.language = "ko";
	config.toolbarGroups = [
        { name: 'basicstyles', groups: [ 'basicstyles'/*, 'cleanup' */] },
        { name: 'paragraph',   groups: [ 'list', 'align', 'bidi' ] },
		{ name: 'styles' },
		{ name: 'colors' },
		{ name: 'clipboard',   groups: [ 'clipboard', 'undo' ] },
		{ name: 'links' },
		//{ name: 'insert' },
		//{ name: 'forms' },
		//{ name: 'tools' },
		{ name: 'document',	   groups: [ 'mode', 'document', 'doctools' ] },
		{ name: 'others' },
		{ name: 'owlet' }
		
	];

	// Remove some buttons provided by the standard plugins, which are
	// not needed in the Standard(s) toolbar.
	//config.removeButtons = 'Underline,Subscript,Superscript';
	config.removeButtons = 'Anchor,Format,Styles,Cut,Copy,Paste,PasteText,PasteFromWord,Subscript,Superscript';

	config.removePlugins= 'image';
	
	// Set the most common block elements.
	config.format_tags = 'p;h1;h2;h3;pre';

	// Simplify the dialog windows.
	config.removeDialogTabs = 'image:advanced;link:advanced';
	
//	config.extraPlugins = 'doksoft_maps';
	
	config.doksoft_table_gui_width = 5;
	config.doksoft_table_gui_height = 5;
	config.doksoft_table_class = 'custom_class';
	config.doksoft_table_default_100 = true;
	
	config.doksoft_advanced_blocks_enabled_by_default = true;
	
	config.doksoft_maps_auto_scaling_on_search = true; 
};
