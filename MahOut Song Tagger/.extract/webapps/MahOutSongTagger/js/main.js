$(document).ready(function() {

	flushMessage();

});

function flushMessage() {

	var flashMessageElements = $('.flash-message');

	flashMessageElements.each(function() {

		var flashMessageElement = $(this);

		var delay = 0;
		if (flashMessageElement.data('delay')) {
			delay = flashMessageElement.data('delay');
		} else {
			delay = 1000;
		}

		if ($(flashMessageElement).is(":visible")) {
			var timeoutId = setTimeout(function() {
				flashMessageElement.fadeOut('slow');
			}, delay);
			flashMessageElement.data('timeoutId', timeoutId);

		} else {
			flashMessageElement.change(function() {
				var timeoutId = setTimeout(function() {
					flashMessageElement.fadeOut('slow');
				}, delay);
				flashMessageElement.data('timeoutId', timeoutId);
			});
		}

		flashMessageElement.mouseenter(function() {
			clearTimeout(flashMessageElement.data('timeoutId'));
			flashMessageElement.fadeIn('slow');
		}).mouseleave(function() {
			var timeoutId = setTimeout(function() {
				flashMessageElement.fadeOut('slow');
			}, delay);
			flashMessageElement.data('timeoutId', timeoutId);
		});

	});

}