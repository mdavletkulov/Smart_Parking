<#import "../parts/common.ftlh" as c>
<@c.page>
    <div class="card-deck">
    <#list parkings as parking>
        <#if (parking.getId()%3==1) || (parking.getId()==1)>
            <div class="row mt-5">
        </#if>
        <div class="card col-4 p-0">
            <a href="/parking/${parking.id}">
            <img src="img/parking_1.jpg" class="card-img-top">
            <div class="card-body">
                <h5 class="card-title">${parking.getDescription()}</h5>
                <p class="card-text">Парковка расчитана на ${parking.getPlaceNumber()} мест</p>
            </div>
            </a>
        </div>
        <#if (parking.getId()%3==0) || (parking.getId()==parkingsSize)>
            </div>
        </#if>

    </#list>
    </div>
</@c.page>