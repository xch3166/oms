var FormRepeater = function () {

    return {
        //main function to initiate the module
        init: function () {
        	$('.mt-repeater').each(function(){
        		$(this).repeater({
        			show: function () {
	                	$(this).slideDown();
                        $('.date-picker').datepicker({
                            rtl: App.isRTL(),
                            orientation: "left",
                            autoclose: true
                        });
		            },

		            hide: function (deleteElement) {
		                if(confirm('确定要删除该项吗?')) {
		                    $(this).slideUp(deleteElement);
		                }
		            },

		            ready: function (setIndexes) {

		            }

        		});
        	});
        }

    };

}();

jQuery(document).ready(function() {
    FormRepeater.init();
});
