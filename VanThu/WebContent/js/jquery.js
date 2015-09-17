/*
 * jQuery Officebar 0.2
 *
 * Copyright (c) 2009 Sven Rymenants
 * GPL (GPL-LICENSE.txt) license.
 *
 */
;(function(jMVP) {
  // private function for debugging
  jMVP.buildOfficeBar = function(object, options) {
    if (jMVP(object).data("officebar")) { return false; } //return if already exist	

    options = jMVP.extend({}, jMVP.fn.officebar.defaults, options);

    var officeBarClass = {
      object: null,

      closeMenu: function() {
      jMVP("div.officetab").hide();
        var menu = jMVP(object).data("officebar.activeMenu");
        if(menu === null) { return; }

        if (menu.type === 0) {
          this.hideSplitMenu(menu.object); }
        else if(menu.type === 1) {
          this.hideDropDown(menu.object); }
      },

      showSplitMenu: function(triggerButton) {
        var me = jMVP(triggerButton).parent();
        var menu = me.data("officebar.menu");
        if(!menu) { return; }

        var activeMenu = jMVP(object).data("officebar.activeMenu");
        if(activeMenu && ((activeMenu.type !== 0) || (activeMenu.object !== menu))) {
          this.hideSplitMenu(activeMenu.object); }
        var eventInfo = { id: me.attr("id"), rel: me.attr("rel") };

        if(options.onBeforeShowSplitMenu) {
          (options.onBeforeShowSplitMenu)(eventInfo); }

        menu.show();
        jMVP(object).data("officebar.activeMenu", {object: menu, type: 0});

        //Fixes repaint bug in IE7
        if(jMVP.browser.msie)
          jMVP("ul", object).css('display', 'none').css('display', 'block');

        if(options.onAfterShowSplitMenu) {
          (options.onAfterShowSplitMenu)(eventInfo); }
      },

      hideSplitMenu: function(menu) {
        //var eventInfo = { id: me.attr("id"), rel: me.attr("rel") };

        if(options.onBeforeHideSplit) {
          (options.onBeforeHideSplit)({id:null, rel:null}); }

        jMVP(menu).hide();

        jMVP(object).data("officebar.activeMenu", null);
        if(options.onAfterHideSplit) {
          (options.onAfterHideSplit)({id:null, rel:null}); }
      },
      
      showDropDown: function(triggerButton) {
        var me = jMVP(triggerButton);
        var eventInfo = { id: me.attr("id"), rel: me.attr("rel") };
        var menu = me.data("officebar.menu");
        var pos = me.offset();

        menu.css({top: pos.top+me.height(), left: pos.left}).show();
        
        jMVP(me).addClass("opened");

        jMVP(object).data("officebar.activeMenu", {object: menu, type: 1});
        if(options.onShowDropdown) {
          (options.onShowDropdown)(eventInfo); }
      },

      hideDropDown: function(dropdown) {
        var b = jMVP("a.opened");
        var eventInfo = { id: b.attr("id"), rel: b.attr("rel") };

        b.removeClass("opened");
        jMVP(dropdown).hide();

        jMVP(object).data("officebar.activeMenu", null);
        if(options.onHideDropdown) {
          (options.onHideDropdown)(eventInfo); }
      },
      
      bindButton: function(ref, fnc) {
        var aRef = ref.split(">");
        switch(aRef.length) {
          case 1: jMVP("a[rel="+aRef[0]+"]", object).bind("click", fnc); break;
          case 2: jMVP("a[rel="+aRef[0]+"] + div>ul>li div a[rel="+aRef[1]+"]", object).bind("click", fnc); break;
        }
      },
      
      selectTab: function(trigger) {
        var activeTab = jMVP(object).data("officebar.activeTab");
        
        this.closeMenu();
        
        if(activeTab) {
          jMVP("div:eq(0)", activeTab)
            .hide()
            .parent()
            .removeClass('current'); }

        jMVP(object).data("officebar.activeTab",
        jMVP(trigger)
          .next()
          .show()
          .end()
          .parent()
          .addClass('current')
          .get(0));
        
        var me = jMVP(trigger);
        if(options.onSelectTab) {
            (options.onSelectTab)({ id: me.attr("id"), rel: me.attr("rel") }); 
        }
      },
      
      dragStart: function(e, obj) {
        var menu = jMVP(obj).parent().parent();
        jMVP(object).data("officebar.resize", {
          object: menu,
          startX: e.pageX,
          startY: e.pageY,
          width: jMVP(obj).width(),
          height: menu.height(),
          handle: parseInt(jMVP(obj).css('margin-top'), 10) });
        jMVP("body").css("cursor", "se-resize");
      },

      dragMove: function(e) {
        var dragData = jMVP(object).data("officebar.resize");
        if(!dragData) { return; }
        
        var diffX = e.pageX - dragData.startX + dragData.width;
        var diffY = e.pageY - dragData.startY + dragData.height;

        var diffHandle = e.pageY - dragData.startY + dragData.handle;
        if(diffHandle > 0)
        {
          jMVP('li.resize', dragData.object).css('margin-top', diffHandle);
          dragData.object.css({width: diffX+"px", height: diffY+"px" });
        }
      },
      
      dragStop: function(e) {
        var dragData = jMVP(object).data("officebar.resize");
        if(!dragData) {
          //var menuData = jMVP(object).data("officebar.activeMenu");
          return;
        }
        jMVP(object).data("officebar.resize", null);
        jMVP("body").css("cursor", "default");
      }
    };
    
    //Object data
    jMVP(object).data("officebar", {
      init: true,
      options: options,
      version: "0.1"});
    jMVP(object).data("officebar.activeMenu", null);
    jMVP(object).data("officebar.activeTab", null);
    jMVP(object).data("officebar.resize", null);

    // create the tab event handler
    jMVP(">ul>li", object).each(function() {
      var isSelected = jMVP(this).hasClass("current");
      if(isSelected) {
        jMVP(object).data("officebar.activeTab", this); }
      
      jMVP(">ul", this).wrap('<div class="officetab"></div>')
        .parent()
        .css('display', isSelected ? 'block' : 'none');
      
      jMVP("a:eq(0)", this).bind("click", function(e) {
        (officeBarClass.selectTab)(this);
        return false;
      });
    });

    //Wrap each li content in panel div
    jMVP("div.officetab>ul>li", object).each(function() {
      jMVP(this).wrapInner('<div class="panel"></div>');
    });

    // small button alignment
    jMVP("div.list>ul, div.textlist>ul", object).each(function() {
      var count = jMVP("li", this).size();
      var style = "";
      if(count === 1) {
        style = ' style="margin-top: 23px"'; }
      else if(count === 2) {
        style = ' style="margin-top: 14px"'; }
      jMVP(this).wrap('<div'+style+'></div>');
    });

    //text button lists
    jMVP("div.textlist li>a", object).each(function() {
      jMVP(this).wrapInner('<span></span>');
    });

    //dropdown lists
    jMVP("li.dropdown ul>li", object).each(function() {
      jMVP(this).wrapInner('<span></span>');
    });

    // split button menu move and event binding
    jMVP("div.split>div", object).each(function() {
      var me = jMVP(this);
      var pos = me.offset();
      
      me.prev() //TODO: check node type
      .data("officebar.menu",
         me.clone()
          .css("display", "none")
          .addClass("buttonsplitmenu")
          .css({top: pos.top, left: pos.left, display: "none"})
          .appendTo(object)
          .bind("click", function(e) {
            if(jMVP(e.target).parent().hasClass("resize")) return;
              (officeBarClass.hideSplitMenu)(this);
          })
        .find("li>a")
        .wrapInner('<span></span>')
        .end()
      );

      //IE fix for popup's: Fix width
      if(jMVP.browser.msie)
      {
        var help = me.prev().data("officebar.menu");
        help.css("width", help.width());
      }
      me.remove();
    });

    // create the split button event handler
    jMVP("div.split>a>span", object).bind("click", function(e) {
      (officeBarClass.showSplitMenu)(this);
      return false; //prevent that the button handler closes the menu again
    });

    // dropdown button menu move and event binding
    jMVP("div.textlist li.dropdown div", object).each(function(){
      var me = jMVP(this);
      me.prev() //TODO: check node type
        .data("officebar.menu",
           me.clone()
          .css("display", "none")
          .addClass("buttonsplitmenu")
          .appendTo(object)
          .bind("click", function(e) {
            (officeBarClass.hideDropDown)(this);
          })
          .find("li>a")
          .wrapInner('<span></span>')
          .end()
        );

      //IE fix for popup's: Fix width
      if(jMVP.browser.msie)
      {
        var help = me.prev().data("officebar.menu");
        help.css("width", help.width());
      }
      me.remove();
    });

    // create the dropdown button menu event handler
    jMVP("div.textlist li.dropdown>a", object).bind("click", function(e) {
      var me = jMVP(this);
      var menu = me.data("officebar.menu");
      if(menu) {
        if(menu.is(":visible")) {
          (officeBarClass.hideDropDown)(me.data("officebar.menu"));
        }
        else {
          (officeBarClass.showDropDown)(this);
        }
      }
    });
    
    //Event handlers: buttons
    jMVP("div.button>a, div.list a").bind("click", function(e) {
      var me = jMVP(this);
      e.preventDefault();
      var eventInfo = { id: me.attr("id"), rel: me.attr("rel") };

      if(jMVP(object).data("officebar.activeMenu")) {
        (officeBarClass.closeMenu)(); }

      if(options.onClickButton) {
        (options.onClickButton)(eventInfo); }
    });

    //Add separator to text boxes
    jMVP(".textboxlist", object).addClass("separator");
    jMVP(".textboxlist:eq(0)", object).removeClass("separator");

    //Separator button alignment
    jMVP("div.textboxlist>ul", object).each(function() {
      var count = jMVP("li", this).size();
      var style = "";
      if(count === 1) {
        style = ' style="margin-top: 23px"'; }
      else if(count === 2) {
        style = ' style="margin-top: 13px"'; }
      jMVP(this).wrap('<div'+style+'></div>');
    });
    
    //Add the resize to the list
    jMVP("div.buttonsplitmenu ul", object).append('<li class="resize"><span>&nbsp;</span></li>');
    jMVP("div.buttonsplitmenu ul li.resize", object).bind("mousedown", function(e) { (officeBarClass.dragStart)(e, this); });

		jMVP(document)
    .bind("mousemove", function(e) { (officeBarClass.dragMove)(e); })
    .bind("mouseup", function(e) { (officeBarClass.dragStop)(e); })
    .bind("hover", function(e) { (officeBarClass.dragStop)(e); })
		;
    
    //Next task
    
		//Publish class
    jMVP(object).data("officebar.class", officeBarClass);
  };

	jMVP.fn.officebar = function(options) {
		return this.each(function() { 
      jMVP.buildOfficeBar(this, options);
    });
	};

  jMVP.fn.officebarBind = function(ref, fnc) {
		return this.each(function() { 
      if(jMVP(this).data("officebar")) {
        jMVP(this).data("officebar.class").bindButton(ref, fnc); }
    });
  };
  
  // plugin defaults
  jMVP.fn.officebar.defaults = {
    onSelectTab: false,
    onBeforeShowSplitMenu: false,
    onAfterShowSplitMenu: false,
    onBeforeHideSplit: false,
    onAfterHideSplit: false,
    onShowDropdown: false,
    onHideDropdown: false,
    onClickButton: true
  };
})(jQuery);
