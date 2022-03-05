function getDeleteBuddy(bookId, buddyJson) {
    return () => {
        fetch("http://localhost:8080/deletebuddy?" + new URLSearchParams({
            bookId: bookId
        }), {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: buddyJson})
            .then(response => {
                if (response.ok) {
                    getFetchBuddies(bookId)();
                }
            });

        document.getElementById("buddy-list-title").innerText = "No AddressBook Selected";
    }
}

function addBuddy() {
    let bookId = document.getElementById("selected-book").value;
    console.log('adding to ' + bookId)
    fetch("http://localhost:8080/addbuddy?" + new URLSearchParams({
        bookId: bookId
    }), {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: "{\"name\": \""
            + document.getElementById("add-buddy-name").value
            + "\", \"phoneNumber\": \""
            + document.getElementById("add-buddy-number").value
            + "\"}"})
        .then(response => {
            if (response.ok) {
                response.json().then(res => {console.log(res)})
                getFetchBuddies(bookId)();
            }
        })
}

function getDeleteAddressBook(bookId) {
    return () => {
        fetch("http://localhost:8080/deletebook?" + new URLSearchParams({
            bookId: bookId
        }), {method: 'POST'})
            .then(response => {
                if (response.ok) {
                    fetchBooks();
                    document.getElementById("buddy-list-title").innerText = "No AddressBook Selected";
                    document.getElementById('buddy-list').innerHTML = "";
                    document.getElementById("buddy-menu").style.display = "none";
                }
            })
    }
}

function getFetchBuddies(bookId) {
    return () => {
        document.getElementById("buddy-list").innerHTML = "";

        fetch("http://localhost:8080/getbuddies?" + new URLSearchParams({
            bookId: bookId
        }), {
            method: 'GET',
            headers: { 'Accept': 'application/json' }})
            .then(response => {
                if (response.ok) {
                    response.json().then((buddies) => {
                        document.getElementById("buddy-list-title").innerText = "Selected AddressBook " + bookId;
                        for (let buddy of buddies) {
                            let buddyRow = document.createElement("div")
                            buddyRow.className = "buddy";
                            let name = document.createElement("div")
                            name.textContent = buddy.name;
                            let number = document.createElement("div")
                            number.textContent = buddy.phoneNumber;
                            let del = document.createElement("div")
                            del.textContent = "DELETE";
                            del.className = "delete";
                            del.onclick = (e) => {
                                e.stopPropagation();
                                getDeleteBuddy(bookId, "{\"name\": \""
                                    + buddy.name
                                    + "\", \"phoneNumber\": \""
                                    + buddy.phoneNumber + "\"}")()
                            }
                            buddyRow.append(name);
                            buddyRow.append(number);
                            buddyRow.append(del);
                            document.getElementById("buddy-list").append(buddyRow);
                        }
                    })
                }
            })
    }
}

function fetchBooks() {
    document.getElementById("book-list").innerHTML = "";

    fetch("http://localhost:8080/getbooks", {
        method: 'GET',
        headers: {'Accept': 'application/json'}
    })
        .then(response => {
            if (response.ok) {
                response.json().then((books) => {
                    for (let book of books) {
                        let bookRow = document.createElement("div");
                        bookRow.className = "book";
                        let name = document.createElement("div");
                        name.textContent = "AddressBook " + book.id;
                        name.className = "view-book";
                        let del = document.createElement("div");
                        del.textContent = "DELETE";
                        del.onclick = (e) => {
                            e.stopPropagation();
                            getDeleteAddressBook(book.id)();
                        }
                        del.className = "delete";
                        bookRow.append(name);
                        bookRow.append(del);
                        bookRow.onclick = () => {
                            getFetchBuddies(book.id)();
                            document.getElementById("buddy-menu").style.display = "grid";
                            document.getElementById("selected-book").setAttribute("value", book.id);
                        }
                        document.getElementById("book-list").append(bookRow);
                    }
                })
            }
        });
}

document.getElementById("get-books").onclick = fetchBooks;

document.getElementById("create-book").onclick = (_) => {
    fetch("http://localhost:8080/createbook", {
        method: 'POST',
        headers: { 'Accept': 'application/json' }})
        .then(response => {
            if (response.ok) {
                fetchBooks();
            }
        })
}

document.getElementById("submit-add-buddy").onclick = addBuddy;

document.getElementById("back").onclick = () => {
    window.location = 'http://localhost:8080/';
}