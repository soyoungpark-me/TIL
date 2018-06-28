/*
 Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights reserved.
 For licensing, see LICENSE.md or http://ckeditor.com/license
*/
(function(){CKEDITOR.plugins.add("embed",{icons:"embed",hidpi:!0,requires:"embedbase",init:function(a){var c=CKEDITOR.plugins.embedBase.createWidgetBaseDefinition(a);CKEDITOR.tools.extend(c,{dialog:"embedBase",button:a.lang.embedbase.button,allowedContent:"div[!data-oembed-url]",requiredContent:"div[data-oembed-url]",providerUrl:new CKEDITOR.template(a.config.embed_provider||"//ckeditor.iframe.ly/api/oembed?url={url}&callback={callback}"),upcast:function(b,a){if("div"==b.name&&b.attributes["data-oembed-url"])return a.url=
b.attributes["data-oembed-url"],!0},downcast:function(a){a.attributes["data-oembed-url"]=this.data.url}},!0);a.widgets.add("embed",c);a.filter.addElementCallback(function(a){if("data-oembed-url"in a.attributes)return CKEDITOR.FILTER_SKIP_TREE})}})})();