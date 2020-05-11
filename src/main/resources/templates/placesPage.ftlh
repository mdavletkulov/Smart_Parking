<#import "parts/common.ftlh" as c>
<@c.page>
    <div class="card-deck">
        <#list places as place>
            <#if (place.getId()%5==1) || (place.getId()==1)>
                <div class="row mt-5">
            </#if>
            <div class="card col-6 p-0">
                <a href="${parking.getId()}/${place.id}">
                    <div class="card-body">
                        <h3 class="card-title">Парковочное место <br> ${place.getId()}</h3>
                    </div>
                </a>
            </div>
            <#if (place.getId()%5==0) || (place.getId()==placesSize)>
                </div>
            </#if>

        </#list>
    </div>
</@c.page>