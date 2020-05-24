<#import "../parts/common.ftlh" as c>

<@c.page>

    <#if message?has_content>
        <div class="alert alert-${messageType} col-4" role="alert">
            ${message}
        </div>
    </#if>
    <div>
        <a href="/parking/adminEvents"
           class="btn btn-dark stretched-link my-3">Назад</a>
    </div>
    <h5>Добавление парковочного события</h5>
    <form action="/parking/addEvent" method="post" class="mt-2 col-sm-4" enctype="multipart/form-data">
        <div>
        <div class="form-group  parking">
            <label class="parkings" for="parking">Парковки</label>
            <select class="form-control parkings" name="parking">
                <#list parkings as parking>
                    <option id="${parking.getId()}" value="${parking.getId()}">${parking.getDescription()}</option>
                </#list>
            </select>
        </div>
            <#if parkingError??>
                <div class="invalid-feedback" style="display: block">
                    ${parkingError}
                </div>
            </#if>
        </div>
        <div class="form-group place ">
            <label class="place" for="place">Парковочное место</label>
            <select class="form-control place" name="place">
            </select>
            <#if placeError??>
                <div class="invalid-feedback" style="display: block">
                    ${placeError}
                </div>
            </#if>
        </div>
        <div class="form-group ">
            <label for="file">Выберите фото автомобиля на парковочном месте</label>
            <input name="image" type="file" class="filestyle">
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <img hidden="hidden" class="download" src="/img/download_gif.gif"/>
        <button type="submit" class="btn btn-primary mt-3 send">Отправить</button>
    </form>
    <script>
        var parkings = [<#list parkings as parking>['${parking.getId()}', "${parking.getDescription()}"], </#list>];
        var parkingSelect = $('select.parkings');
        var parkingLable = $('label.parkings');
        var placeSelect = $('select.place');
        var placeLabel = $('label.place');
        $(".filestyle").filestyle({text: "Выберите фото"});

        $(document).ready(function () {


            getDefaultPlaces();

            parkingSelect.change(function () {
                var selectedParking = $(this).children("option:selected").attr('id');
                places(selectedParking);
            });

            $('button.send').click(function () {
                $('img.download').removeAttr('hidden');
            })

        });

        function places(selectedParking) {
            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: "/parking/getPlaces/" + selectedParking,
                dataType: 'json',
                cache: false,    //This will force requested pages not to be cached by the browser
                timeout: 600000,
                success: function (data) {
                    placeSelect.empty();
                    for (var i = 0; i < data.length; i++) {
                        populatePlace('select.place', data[i]);
                    }
                }
            });
        }

        function getDefaultPlaces() {
            var selectedParking = $(parkingSelect).children("option:selected").attr('id');
            places(selectedParking);
        }

        function populatePlace(selector, name) {
            $(selector)
                .append('<option>' + name + '</option>')
        }
    </script>
</@c.page>