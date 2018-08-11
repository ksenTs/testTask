document.onkeyup = function (e) {
    e = e || window.event;
    if (e.keyCode === 13) {
        update();
    }
    return false;
};

function update(){

    var req = $("#search_req").val();

    if(req !== ""){
        $.ajax({
            url:"/findGood",
            type: "POST",
            data:{
                search_req: req,
                filter: document.querySelector('input[name="filter"]:checked').value
            },
            success: function (data) {
                clear();
                var count = 0;
                if(data.length>0) {
                    data.forEach(function (t) {
                        count = count + 5;
                        draw(t, count);
                    })
                }
                else document.getElementById("main").innerHTML = "Oops, nothing";
            },
            dataType: "json"
        });
    }
    else
        alert("пожалуйста, заполните поле поиска");


}

function draw(t, count) {
    var mainDiv = document.getElementById("main");
    var div2 = document.createElement("div");
    div2.setAttribute("class","info");
    div2.setAttribute("id",t.goodId);

    var li1 = document.createElement("h3");
    li1.innerHTML = "Name: ";
    if (t.goodName.length !== 0) {
        var b0 = document.createElement("input");
        b0.setAttribute("type", "text");
        b0.setAttribute("class","info");
        b0.setAttribute("value", t.goodName);
        b0.setAttribute("id", count);
        li1.appendChild(b0);
    } else {
        var i0 = document.createElement("i");
        i0.innerHTML = "unknown";
        li1.appendChild(i0);
    }

    count++;
    var li2 = document.createElement("h3");
    li2.innerHTML ="Description: ";
    if (t.description.length !== 0) {
        var b1 = document.createElement("input");
        b1.setAttribute("type", "text");
        b1.setAttribute("class","info");
        b1.setAttribute("value", t.description);
        b1.setAttribute("id", count);
        li2.appendChild(b1);
    } else {
        var i1 = document.createElement("i");
        i1.innerHTML = "unknown";
        li2.appendChild(i1);
    }

    count++;
    var li4 = document.createElement("input");
    li4.setAttribute("type", "hidden");
    li4.setAttribute("value", t.goodId);
    li4.setAttribute("id", count);

    $.ajax({
        url:"/getCategoryList",
        type: "POST",
        data:{
            goodName:t.goodName
        },
        success: function (data) {

            var li3 = document.createElement("h3");
            li3.innerHTML ="Category: ";
            div2.appendChild(li3);
            data.forEach(function (t) {
                var li5 = document.createElement("h3");
                li5.innerHTML = t.categoryName;
                div2.appendChild(li5);
            })
           },
        dataType: "json"
    });


    count++;
    var butEdit = document.createElement("input");
    butEdit.setAttribute("type", "button");
    butEdit.setAttribute("value", "CHANGE");
    butEdit.setAttribute("id", count);
    butEdit.setAttribute("class", "but");
    butEdit.setAttribute("onclick", "edit(this)");

    count++;
    var butDelete = document.createElement("input");
    butDelete.setAttribute("type", "button");
    butDelete.setAttribute("value", "DELETE");
    butDelete.setAttribute("id", count);
    butDelete.setAttribute("class", "but");
    butDelete.setAttribute("onclick", "remove(this)");

    div2.appendChild(li1);
    div2.appendChild(li2);
    div2.appendChild(li4);

    div2.appendChild(butEdit);
    div2.appendChild(butDelete);

    mainDiv.appendChild(div2);


}
function clear() {
    var mainDiv = document.getElementById("main");
    while (mainDiv.firstChild) {
        mainDiv.removeChild(mainDiv.firstChild);
    }
}

function edit(obj) {

    var nameId = obj.id - 3;
    var getId = obj.id - 1;
    var descrId = obj.id - 2;

    var newName = document.getElementById(nameId.toString()).value;
    var newDescr = document.getElementById(descrId.toString()).value;
    var goodId = document.getElementById(getId.toString()).value;

    if(newName !== "" && newDescr !== "")
    {
        $.ajax({
            url : "/editGood",
            data: {
                "newName":newName,
                "newDescr":newDescr,
                "id":goodId
            },
            error : function(data) {
                alert("ошибка");

            },
            success : function (data) {
                alert("изменения сохранены");
            }
        }); }
        else
            alert("пожалуйста, заполните пустые поля");
}

function remove(obj) {

    var getId = obj.id - 2;
    var goodId = document.getElementById(getId.toString()).value;

    $.ajax({
        url : "/removeGood",
        data: {
            "id":goodId
        },
        error : function(data) {
            alert("ошибка");

        },
        success : function (data) {
            alert("изменения сохранены");
            var remDiv = document.getElementById(goodId);
            while (remDiv.firstChild) {
                remDiv.removeChild(remDiv.firstChild);
            }
        }
    });
}
