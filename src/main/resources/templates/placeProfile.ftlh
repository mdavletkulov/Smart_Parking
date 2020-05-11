<#import "parts/common.ftlh" as c>
<@c.page>
    <h4>${parkingName}</h4>
    <h5 class="mt-3">Парковочное место номер ${placeNum}</h5>
    <table class="table table-borderless table-striped col-4">
        <thead>
        <tr class="d-flex">
            <th scope="col" class="col-1"></th>
            <th scope="col" class="col-1"></th>
        </tr>
        </thead>
        <tbody>
        <tr class="d-flex">
            <th scope="row">Номер автомобиля</th>
            <td>${automobile.getNumber()}</td>
        </tr>
        <tr class="d-flex">
            <th scope="row">Автомобиль</th>
            <td>${automobile.getInfo()}</td>
        </tr>
        <tr class="d-flex">
            <th scope="row">Владелец</th>
            <td>${person.getFullName()}</td>
        </tr>
        <tr class="d-flex">
            <th scope="row">Должность</th>
            <#if person.isStudent()>
                <td>Студент</td>
            </#if>
            <#if person.isEmployee()>
                <td>${person.getJobPosition().getNamePosition()}</td>
            </#if>
        </tr>

        <#if person.getSubdivision()??>
            <tr class="d-flex">
                <th scope="row">Институт</th>
                <td>${person.getSubdivision().getDivision().getName()}</td>
            </tr>
            <tr class="d-flex">
                <th scope="row">Кафедра</th>
                <td>${person.getSubdivision().getName()}</td>
            </tr>
        </#if>

        <#if person.getCourse()??>
            <tr class="d-flex">
                <th scope="row">Курс</th>
                <td>${person.getCourse()}</td>
            </tr>
        </#if>

        <#if person.getGroupName()??>
            <tr class="d-flex">
                <th scope="row">Группа</th>
                <td>${person.getGroupName()}</td>
            </tr>
        </#if>



        <tr class="d-flex">
            <th scope="row">Дата начала парковки</th>
            <td>${parkingEvent.getStartTime()}</td>
        </tr>
        <tr class="d-flex">
            <th scope="row">Место для инвалидов</th>
            <#if specialStatus>
                <td content="\2611">&#9745;</td>
            <#else>
                <td content="\2612">&#x2716;</td>
            </#if>
        </tr>
        <#if person.getPassNum()??>
            <tr class="d-flex">
                <th scope="row">Пропуск номер:</th>
                <td>${person.getPassNum()}</td>
            </tr>
        <#else>
            <tr class="d-flex bg-danger">
                <th scope="row" class="bg-danger">Пропуск</th>
                <td class="bg-danger">Пропуска нет или он просрочен!</td>
            </tr>
        </#if>
        <tr class="d-flex">
            <#if violation>
                <td class="bg-danger w-100">Нарушение правил парковки на место для инвалидов!</td>
            </#if>
        </tr>


        </tbody>
    </table>
</@c.page>