$(document).ready(function() {
  $("code").parent().each(function(i, e) {
    var $e = $(e);
    var toggleSnippet = $("<a href='#'>Toggle Source</a>");
    toggleSnippet.on('click', function() {
      $e.toggle();
    });

    toggleSnippet.insertBefore($e);
    $e.hide();
  });
});


hljs.initHighlightingOnLoad();
