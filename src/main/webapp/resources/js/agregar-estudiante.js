(function($, undefined){
    $(document).ready(function(){

        if( $("#transferido").is(":checked") )
            $("#camposTransferidos").show();

        $("#transferido").on("change", function(){
            $("#camposTransferidos").toggle("fast");
        });
    });
})($);
