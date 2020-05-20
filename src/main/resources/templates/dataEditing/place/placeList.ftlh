<#import "../../parts/common.ftlh" as c>

<@c.page>
    <script type="text/javascript" src="/static/js/tableSearch.js"></script>
    <div><h5 class="mb-3">Парковочные места</h5>
    </div>

    <div class="form-group" style="margin-bottom: 5%">
        <input type="text" class="form-control pull-right" id="search" placeholder="Поиск по таблице">
    </div>
    <table class="table table-striped text-center table-hover col-6" id="persons">
        <thead class="thead-dark">
        <tr class="row">
            <th class="align-middle col-sm-4" scope="col">Парковка</th>
            <th class="align-middle col-sm-1" scope="col">Номер места</th>
            <th class="align-middle col-sm-2" scope="col">Место для инвалидов</th>
            <th class="align-middle col-sm-5" class="align-middle" colspan="2" scope="col">
                <a href="/dataEdit/place/add" class="btn btn-primary stretched-link">
                    Добавить новое парковочное место</a></th>
        </tr>
        </thead>
        <tbody>
        <#list places as place>
            <tr class="row">
                <th class="align-middle col-sm-4" scope="row">${place.getParking().getDescription()}</th>
                <td class="align-middle col-sm-1" scope="row">${place.getPlaceNumber()}</td>
                <td class="align-middle col-sm-2" scope="row"><#if place.isSpecialStatus()>&#9745; <#else>&#x2716;</#if></td>
                <td class="col-sm-3"><a href="/dataEdit/place/edit/${place.id}"
                                        class="btn btn-primary stretched-link">Редактировать</a>
                </td>
                <td class="col-sm-2">
                    <button id="${place.getId()}" type="button" class="btn btn-primary askDelete"
                            data-toggle="modal">
                        Удалить
                    </button>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>

    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Подтвердите удаление</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    Вы действительно хотите удалить парковочное место?
                </div>
                <div class="modal-footer">
                    <button id="delete" type="button" class="btn btn-secondary">Удалить</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal">Отменить</button>
                </div>
            </div>
        </div>
    </div>

    <script>
        var userId;
        jQuery(document).ready(function ($) {
            $('button.askDelete').click(function () {
                jQuery.noConflict();
                $('#exampleModal').modal('show');
                placeId = $(this).attr('id');
            });
        });

        $('button#delete').click(function () {
            window.location.href = '/dataEdit/place/delete/' + placeId;
            return false;
        });
    </script>

</@c.page>