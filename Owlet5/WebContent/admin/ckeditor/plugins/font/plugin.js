/**
 * @license Copyright (c) 2003-2014, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

( function() {
	function addCombo( editor, comboName, styleType, lang, entries, defaultLabel, styleDefinition, order ) {
		var config = editor.config,
			style = new CKEDITOR.style( styleDefinition );

		// Gets the list of fonts from the settings.
		var names = entries.split( ';' ),
			values = [];

		// Create style objects for all fonts.
		var styles = {};
		for ( var i = 0; i < names.length; i++ ) {
			var parts = names[ i ];

			if ( parts ) {
				parts = parts.split( '/' );

				var vars = {},
					name = names[ i ] = parts[ 0 ];

				vars[ styleType ] = values[ i ] = parts[ 1 ] || name;

				styles[ name ] = new CKEDITOR.style( styleDefinition, vars );
				styles[ name ]._.definition.name = name;
			} else
				names.splice( i--, 1 );
		}

		editor.ui.addRichCombo( comboName, {
			label: lang.label,
			title: lang.panelTitle,
			toolbar: 'styles,' + order,
			allowedContent: style,
			requiredContent: style,

			panel: {
				css: [ CKEDITOR.skin.getPath( 'editor' ) ].concat( config.contentsCss ),
				multiSelect: false,
				attributes: { 'aria-label': lang.panelTitle }
			},

			init: function() {
				this.startGroup( lang.panelTitle );

				for ( var i = 0; i < names.length; i++ ) {
					var name = names[ i ];

					// Add the tag entry to the panel list.
					this.add( name, styles[ name ].buildPreview(), name );
				}
			},

			onClick: function( value ) {
				editor.focus();
				editor.fire( 'saveSnapshot' );

				var style = styles[ value ];

				editor[ this.getValue() == value ? 'removeStyle' : 'applyStyle' ]( style );
				editor.fire( 'saveSnapshot' );
			},

			onRender: function() {
				editor.on( 'selectionChange', function( ev ) {
					var currentValue = this.getValue();

					var elementPath = ev.data.path,
						elements = elementPath.elements;

					// For each element into the elements path.
					for ( var i = 0, element; i < elements.length; i++ ) {
						element = elements[ i ];

						// Check if the element is removable by any of
						// the styles.
						for ( var value in styles ) {
							if ( styles[ value ].checkElementMatch( element, true, editor ) ) {
								if ( value != currentValue )
									this.setValue( value );
								return;
							}
						}
					}

					// If no styles match, just empty it.
					this.setValue( '', defaultLabel );
				}, this );
			},

			refresh: function() {
				if ( !editor.activeFilter.check( style ) )
					this.setState( CKEDITOR.TRISTATE_DISABLED );
			}
		} );
	}

	CKEDITOR.plugins.add( 'font', {
		requires: 'richcombo',
		lang: 'af,ar,bg,bn,bs,ca,cs,cy,da,de,el,en,en-au,en-ca,en-gb,eo,es,et,eu,fa,fi,fo,fr,fr-ca,gl,gu,he,hi,hr,hu,id,is,it,ja,ka,km,ko,ku,lt,lv,mk,mn,ms,nb,nl,no,pl,pt,pt-br,ro,ru,si,sk,sl,sq,sr,sr-latn,sv,th,tr,tt,ug,uk,vi,zh,zh-cn', // %REMOVE_LINE_CORE%
		init: function( editor ) {
			var config = editor.config;

			addCombo( editor, 'Font', 'family', editor.lang.font, config.font_names, config.font_defaultLabel, config.font_style, 30 );
			addCombo( editor, 'FontSize', 'size', editor.lang.font.fontSize, config.fontSize_sizes, config.fontSize_defaultLabel, config.fontSize_style, 40 );
		}
	} );
} )();

/**
 * The list of fonts names to be displayed in the Font combo in the toolbar.
 * Entries are separated by semi-colons (`';'`), while it's possible to have more
 * than one font for each entry, in the HTML way (separated by comma).
 *
 * A display name may be optionally defined by prefixing the entries with the
 * name and the slash character. For example, `'Arial/Arial, Helvetica, sans-serif'`
 * will be displayed as `'Arial'` in the list, but will be outputted as
 * `'Arial, Helvetica, sans-serif'`.
 *
 *		config.font_names =
 *			'Arial/Arial, Helvetica, sans-serif;' +
 *			'Times New Roman/Times New Roman, Times, serif;' +
 *			'Verdana';
 *
 *		config.font_names = 'Arial;Times New Roman;Verdana';
 *
 * @cfg {String} [font_names=see source]
 * @member CKEDITOR.config
 */

/*'Arial/Arial, Helvetica, sans-serif;' +
'Comic Sans MS/Comic Sans MS, cursive;' +
'Courier New/Courier New, Courier, monospace;' +
'Georgia/Georgia, serif;' +
'Lucida Sans Unicode/Lucida Sans Unicode, Lucida Grande, sans-serif;' +
'Tahoma/Tahoma, Geneva, sans-serif;' +
'Times New Roman/Times New Roman, Times, serif;' +
'Trebuchet MS/Trebuchet MS, Helvetica, sans-serif;' +
'Verdana/Verdana, Geneva, sans-serif;' +
'궁서;' +
'한컴바탕;' +
'신명조;' +
'돋움체;' +
'나눔고딕;' +
'판본체;' +
'엽서;' +
'Monaco;' +
'BroadwayEngraved BT;' +
'David;' +
'HY바다M;' +
'HY그래픽;' +
'HY동녘B;' +
'HY크리스탈M;' +
'HY센스L;' +
'HY백송B;' +
'Freehand591 BT;' +
'MS PMincho;' +
'MurrayHill Bd BT;' +
'Yj WADAG;' +
'가는안상수체;' +
'굵은안상수체;' +
'펜흘림;' +
'함초롬돋움;' +
'함초롬바탕;' +
'휴먼가는샘체;' +
'휴먼명조;' +
'휴먼옛체';*/
CKEDITOR.config.font_names = 
	'Arial/Arial, Helvetica, sans-serif;' +
	'Comic Sans MS/Comic Sans MS, cursive;' +
	'Courier New/Courier New, Courier, monospace;' +
	'Georgia/Georgia, serif;' +
	'Lucida Sans Unicode/Lucida Sans Unicode, Lucida Grande, sans-serif;' +
	'Tahoma/Tahoma, Geneva, sans-serif;' +
	'Times New Roman/Times New Roman, Times, serif;' +
	'Trebuchet MS/Trebuchet MS, Helvetica, sans-serif;' +
	'Verdana/Verdana, Geneva, sans-serif;' +
	'궁서;' +
	'한컴바탕;' +
	'신명조;' +
	'돋움체;' +
	'나눔고딕;' +
	'HY궁서;' +
	'HY강M;' +
	'Monaco;' +
	'BroadwayEngraved BT;' +
	'David;' +
	'HY바다M;' +
	'HY그래픽;' +
	'HY동녘B;' +
	'HY크리스탈M;' +
	'Freehand591 BT;' +
	'MS PMincho;' +
	'MurrayHill Bd BT;' +
	'Yj WADAG;' +
	'HY견고딕;' +
	'HY견명조;' +
	'HY나무B;' +
	'HY나무L;' +
	'HY동녘M;' +
	'HY목판L;' +
	'HY바다L;' +
	'HY백송B;' +
	'HY산B;' +
	'HY센스L;' +
	'HY수평선B;' +
	'HY수평선M;' +
	'HY엽서M;' +
	'HY울릉도B;' +
	'HY울릉도M;' +
	'HY태백B;' +
	'MD개성체;' +
	'MD솔체;' +
	'MD아롱체;' +
	'MD아트체;' +
	'MD이솝체;' +
	'Liberty BT;' +
	'Microsoft PhagsPa;' +
	'Microsoft New Tai Lue;' +
	'MingLiU;' +
	'MingLiU_HKSCS;' +
	'Modern;' +
	'Mongolian Baiti;' +
	'MoolBoran;' +
	'MS Gothic;' +
	'MS Mincho;' +
	'MS PGothic;' +
	'MS PMincho;' +
	'MS Sans Serif;' +
	'MS Serif;' +
	'MS UI Gothic;' +
	'MV Boli;' +
	'Narkisim;' +
	'Newtext Bk BT;' +
	'NSimSun;' +
	'Nyala;' +
	'OCR-A BT;' +
	'OCR-B-10 BT;' +
	'Orator10 BT;' +
	'Orbit-B BT;' +
	'Palatino Linotype;' +
	'ParkAvenue BT;' +
	'Plantagenet Cherokee;' +
	'PMingLiU;' +
	'PMingLiU-ExtB;' +
	'Raavi;' +
	'Rod;' +
	'Roman;' +
	'Sakkal Majalla;' +
	'Script;' +
	'Segoe Marker;' +
	'Segoe Print;' +
	'Segoe UI;' +
	'Segoe UI Symbol;' +
	'Shonar Bangla;' +
	'Shruti;' +
	'SimHei;' +
	'Simplified Arabic;' +
	'Simplified Arabic Fixed;' +
	'SimSun;' +
	'SketchFlow Print;' +
	'Stencil BT;' +
	'Swis721 BT;' +
	'Sylfaen;' +
	'Symbol;' +
	'System;' +
	'Tahoma;' +
	'Terminal;' +
	'Times New Roman;' +
	'Traditional Arabic;' +
	'Trebuchet MS;' +
	'Tunga;' +
	'Utssah;' +
	'Vani;' +
	'Verdana;' +
	'Vijaya;' +
	'Vrinda;' +
	'Webdings;' +
	'Wingdings;' +
	'Yj BACDOO;' +
	'YJ BELLA;' +
	'Yj BLOCK;' +
	'Yj BONMOKGAK;' +
	'Yj BUTGOT;' +
	'Yj CHMSOOT;' +
	'Yj DOOLGI;' +
	'Yj GOTGAE;' +
	'Yj INITIALPOSITIVE;' +
	'YJ INJANG;' +
	'Yj MAEHWA SemiBold Yj MAEHWA;' +
	'Yj NANCHO;' +
	'Yj SHANALL;' +
	'Yj SOSEL;' +
	'Yj TEUNTEUN;' +
	'궁서체;' +
	'돋움;' +
	'맑은 고딕;' +
	'문체부 궁체 정자체;' +
	'문체부 궁서 흘림체;' +
	'문체부 돋음체;' +
	'문체부 바탕체;' +
	'문체부 쓰기 정체;' +
	'문체부 쓰기 흘림체;' +
	'문체부 제목 바탕체;' +
	'문체부 훈민정음체;' +
	'바탕;' +
	'바탕체;' +
	'양재깨비체B;' +
	'태 나무;' +
	'펜흘림;' +
	'햔양해서;' +
	'한컴 바겐세일 B;' +
	'한컴 바겐세일 M;' +
	'한컴 백제 B;' +
	'한컴 백제 M;' +
	'한컴 소망 B;' +
	'한컴 소망 M;' +
	'한컴 솔잎 B;' +
	'한컴 솔잎 M;' +
	'한컴 윤고딕 230;' +
	'한컴 윤고딕 240;' +
	'한컴 윤고딕 250;' +
	'한컴 윤체 B;' +
	'한컴 윤체 L;' +
	'한컴 윤체 M;' +
	'한컴 쿨재즈 B;' +
	'한컴 쿨재즈 M;' +
	'한컴바탕확장;' +
	'휴먼가는팸체;' +
	'휴먼굵은샘체;' +
	'가는안상수체;' +
	'굵은안상수체;' +
	'함초롬돋움;' +
	'함초롬바탕;' +
	'휴먼가는샘체;' +
	'휴먼명조;' +
	'휴먼옛체';

/**
 * The text to be displayed in the Font combo is none of the available values
 * matches the current cursor position or text selection.
 *
 *		// If the default site font is Arial, we may making it more explicit to the end user.
 *		config.font_defaultLabel = 'Arial';
 *
 * @cfg {String} [font_defaultLabel='']
 * @member CKEDITOR.config
 */
CKEDITOR.config.font_defaultLabel = '';

/**
 * The style definition to be used to apply the font in the text.
 *
 *		// This is actually the default value for it.
 *		config.font_style = {
 *			element:		'span',
 *			styles:			{ 'font-family': '#(family)' },
 *			overrides:		[ { element: 'font', attributes: { 'face': null } } ]
 *     };
 *
 * @cfg {Object} [font_style=see example]
 * @member CKEDITOR.config
 */
CKEDITOR.config.font_style = {
	element: 'span',
	styles: { 'font-family': '#(family)' },
	overrides: [ {
		element: 'font', attributes: { 'face': null }
	} ]
};

/**
 * The list of fonts size to be displayed in the Font Size combo in the
 * toolbar. Entries are separated by semi-colons (`';'`).
 *
 * Any kind of "CSS like" size can be used, like `'12px'`, `'2.3em'`, `'130%'`,
 * `'larger'` or `'x-small'`.
 *
 * A display name may be optionally defined by prefixing the entries with the
 * name and the slash character. For example, `'Bigger Font/14px'` will be
 * displayed as `'Bigger Font'` in the list, but will be outputted as `'14px'`.
 *
 *		config.fontSize_sizes = '16/16px;24/24px;48/48px;';
 *
 *		config.fontSize_sizes = '12px;2.3em;130%;larger;x-small';
 *
 *		config.fontSize_sizes = '12 Pixels/12px;Big/2.3em;30 Percent More/130%;Bigger/larger;Very Small/x-small';
 *
 * @cfg {String} [fontSize_sizes=see source]
 * @member CKEDITOR.config
 */
CKEDITOR.config.fontSize_sizes = '8/8px;9/9px;10/10px;11/11px;12/12px;14/14px;16/16px;18/18px;20/20px;22/22px;24/24px;26/26px;28/28px;36/36px;48/48px;72/72px;100/100px;127/127px';

/**
 * The text to be displayed in the Font Size combo is none of the available
 * values matches the current cursor position or text selection.
 *
 *		// If the default site font size is 12px, we may making it more explicit to the end user.
 *		config.fontSize_defaultLabel = '12px';
 *
 * @cfg {String} [fontSize_defaultLabel='']
 * @member CKEDITOR.config
 */
CKEDITOR.config.fontSize_defaultLabel = '';

/**
 * The style definition to be used to apply the font size in the text.
 *
 *		// This is actually the default value for it.
 *		config.fontSize_style = {
 *			element:		'span',
 *			styles:			{ 'font-size': '#(size)' },
 *			overrides:		[ { element :'font', attributes: { 'size': null } } ]
 *		};
 *
 * @cfg {Object} [fontSize_style=see example]
 * @member CKEDITOR.config
 */
CKEDITOR.config.fontSize_style = {
	element: 'span',
	styles: { 'font-size': '#(size)' },
	overrides: [ {
		element: 'font', attributes: { 'size': null }
	} ]
};