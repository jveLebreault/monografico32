/**
 * Created by Jose Elias on 06/12/2016.
 */

(function($, undefined){
    $(document).ready(function(){

        $("#transferido").on("change", function(){
            $("#camposTransferidos").toggle("slow");
        });
    });
})($);
