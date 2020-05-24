<#import "../parts/common.ftlh" as c>
<@c.page>
    <div>
        <a href="/parking"
           class="btn btn-dark stretched-link">Назад</a>
    </div>
    <div class="card-deck">
        <#list places as place>
            <#if (place.getId()%5==1) || (place.getId()==1)>
                <div class="row mt-5">
            </#if>
            <div class="card col-6 p-0">
                <a href="${parking.getId()}/${place.id}">
                    <div class="card-body">
                        <h3 class="card-title" id="${place.getPlaceNumber()}">Парковочное место <br> ${place.getId()}</h3>
                    </div>
                </a>
            </div>
            <#if (place.getId()%5==0) || (place.getId()==placesSize)>
                </div>
            </#if>

        </#list>
    </div>

    <script type="text/javascript">
        var parkingId = "${parking.getId()}";

        function foo() {
            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: "updateStatus/"+parkingId,
                dataType: 'json',
                cache: false,
                timeout: 600000,
                success: function (data) {
                    for (var i=0; i <data.length; i++ )
                    {
                        var placeNum = data[i].placeNum;
                        var placeNumElement = $('#'+ placeNum);
                        placeNumElement.removeClass('active');
                        placeNumElement.removeClass('viol');
                        placeNumElement.removeClass('free');
                        if (data[i].active) {
                            placeNumElement.removeClass('active');
                            placeNumElement.removeClass('viol');
                            placeNumElement.removeClass('free');
                            placeNumElement.addClass('active');
                        }
                        if (data[i].violation) {
                            placeNumElement.removeClass('active');
                            placeNumElement.removeClass('viol');
                            placeNumElement.removeClass('free');
                            placeNumElement.addClass('viol');
                        }
                        if (!data[i].violation  && !data[i].active) {
                            placeNumElement.removeClass('active');
                            placeNumElement.removeClass('viol');
                            placeNumElement.removeClass('free');
                            placeNumElement.addClass('free');
                        }
                        console.log(data[i].placeNum + " " + data[i].violation + " " + data[i].active);
                    }
                },
                error: function (e) {
                    alert(e);
                }
            });
            setTimeout(foo, 4000);
        }


        $(document).ready(foo());
    </script>


</@c.page>