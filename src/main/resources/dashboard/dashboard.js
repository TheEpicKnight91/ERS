let user;
let statusMenuElem;
let Status;
let reimId;

window.onload = async function()
{
    let response = await fetch(`${domain}/session`);

    let responseBody = await response.json();

    if (!responseBody.success)
    {
        window.location = "../";
    }

    const params = new Proxy(new URLSearchParams(window.location.search), {
  get: (searchParams, prop) => searchParams.get(prop),
});
    

    console.log(params.userId);
    user = responseBody.data;
    Status = params.status;
    console.log(params);
    console.log(params.status);
    console.log(Status);

    let styleSheetElem = document.getElementsByTagName('link')[2];
    let welcomeElem = document.getElementById("welcome");

    welcomeElem.innerText = `Welcome ${user.firstName} ${user.lastName}`;

    let statusspot = document.getElementById("status");
    if (user.role == "MANAGER")
    {
        styleSheetElem.setAttribute('href','./managerdashboard.css')
        document.getElementById("new-reimbursements").style.display = "none";
        statusMenuElem = document.createElement("select");
        statusMenuElem.id = "dropmenu";
        statusMenuElem.addEventListener("change", getReimbursementsByStatus);
        statusMenuElem.innerHTML = `<option value="STATUS">Status</option>
        <option value="PENDING">Pending</option>
        <option value="APPROVED">Approved</option>
        <option value="DENIED">Denied</option>`

        statusspot.append(statusMenuElem);
        for (option of statusMenuElem)
        {
            if (option.value == Status)
            {
                option.selected = true;
                break;
            }
        }
    }
    else
    {
        styleSheetElem.setAttribute('href','./employeedashboard.css')
        statusspot.innerText = "Status";
    }
    getReimbursements();
}

async function getReimbursements()
{
    //send the http request
    let response = await fetch(`${domain}/reimbursement?role=${user.role}&userId=${user.id}&status=${Status}`);

    //retrieve the response body
    let responseBody = await response.json();

    console.log(responseBody);

    let reimbursements = responseBody.data;

    reimbursements.forEach(reim => {
       createReimbursements(reim); 
    });
}

function createReimbursements(reimbursement)
{

    /*<tr>
                <td>row 1 col 1</td> id
                <td>row 1 col 2</td> author
                <td>row 1 col 3</td> date created
                <td>row 1 col 4</td> amount
                <td>row 1 col 5</td> type
                <td>row 1 col 6</td> status
                <td><button id="view-btn-${reimbursement.id}" class="btn btn-primary">View</button>
            </tr>
    */
    let reimContainerElem = document.getElementById("reimbursement-list");

    let reimRecordElem = document.createElement("tr");

    reimRecordElem.className = "reim-record";

    reimRecordElem.innerHTML = `
        <td>${reimbursement.id}</td>
        <td>${reimbursement.authorId}</td>
        <td>${reimbursement.creation_Date}
        <td>${reimbursement.amount}</td>
        <td>${reimbursement.type}</td>
        <td>${reimbursement.status}</td>
        <td><button id="view-btn" class="btn btn-primary" value="${reimbursement.id}" onclick="viewReimbursement(this.value)">View</button></td>`

    reimContainerElem.appendChild(reimRecordElem);
}

document.getElementById("new-reimbursements").addEventListener("click", async function (event)
{
    event.preventDefault();
    document.getElementsByClassName("opacity1")[0].style.display = "inline";
});

document.getElementById("cancelbtn").addEventListener("click", async function(event)
{
    event.preventDefault();
    document.getElementsByClassName("opacity1")[0].style.display = "none";
});

document.getElementById("create-reim").addEventListener("click", async function (event)
{
    event.preventDefault();
    //retrieve input elements from the dom
    let messageElem = document.getElementById("message");
    let amountInputElem = document.getElementById("amount");
    let descriptionInputElem = document.getElementById("description");
    let roleInputElem = document.querySelectorAll('input[name="reim-type"]');

    if (amountInputElem.value == "")
    {
        messageElem.innerText = "Please enter an Amount";
    }
    else
    {
        let type;
        let selected = false;
        for(let radio of roleInputElem)
        {
            if (radio.checked)
            {
                type = radio.value;
                selected = true;
                break;
            }
        }
        if (selected == false)
        {
            messageElem.innerText = "Please selected one of the roles";
        }
        else
        {
            let reimbursement = 
            {
                amount : amountInputElem.value,
                description : descriptionInputElem.value,
                authorId : user.id,
                status : "PENDING",
                type : type
            }
            let response = await fetch(`${domain}/reimbursement/create`,{
                method: "POST",
                body: JSON.stringify(reimbursement)
            });

            let responseBody = await response.json();

            console.log(responseBody.data);

            document.getElementsByClassName("opacity")[0].style.display = "none";

            window.location = `?userId=${user.id}&status=${Status}`;
        }
    }
});

document.getElementById("logout").addEventListener("click", async function(event)
{
    event.preventDefault();
    let response = await fetch(`${domain}/session`,{
        method: "DELETE"
    });
    window.location = "../";
});

function getReimbursementsByStatus()
{
    console.log(statusMenuElem.value);
    window.location = `?userId=${user.id}&status=${statusMenuElem.value}`
};

async function viewReimbursement(reimID)
{
    //send the http request
    let response = await fetch(`${domain}/reimbursement/${reimID}`);

    //retrieve the response body
    let responseBody = await response.json();

    console.log(responseBody);

    let reimbursementinfo = responseBody.data;

    let reimFormContainer = document.getElementById("reim-info");

    let reimInfoElem = document.createElement("tr");

    reimInfoElem.id = "reim-info-elem";

    reimId = `${reimbursementinfo.id}`;
    reimInfoElem.innerHTML = `
        <td>${reimbursementinfo.id}</td>
        <td>${reimbursementinfo.creation_Date}
        <td>${reimbursementinfo.amount}</td>
        <td>${reimbursementinfo.type}</td>
        <td>${reimbursementinfo.status}</td>
        <td>${reimbursementinfo.resolution_Date}`

    reimFormContainer.appendChild(reimInfoElem);

    let creatorFormContainer = document.getElementById("creator-info");

    let creatorInfoElem = document.createElement("tr");

    creatorInfoElem.id = "creator-info-elem";

    creatorInfoElem.innerHTML = `
        <td>${reimbursementinfo.author.id}</td>
        <td>${reimbursementinfo.author.first_name}
        <td>${reimbursementinfo.author.last_name}</td>
        <td>${reimbursementinfo.author.email}</td>`

    creatorFormContainer.appendChild(creatorInfoElem);

    let resolverFormContainer = document.getElementById("resolver-info");

    let resolverInfoElem = document.createElement("tr");

    resolverInfoElem.id = "creator-info-elem";

    if(reimbursementinfo.resolver != null)
    {
        resolverInfoElem.innerHTML = `
            <td>${reimbursementinfo.resolver.id}</td>
            <td>${reimbursementinfo.resolver.first_name}
            <td>${reimbursementinfo.resolver.last_name}</td>
            <td>${reimbursementinfo.resolver.email}</td>`
    }

    resolverFormContainer.appendChild(resolverInfoElem);

    let descript = document.getElementById("reim-description");

    descript.innerText = `${reimbursementinfo.description}`;

    if(user.role != "MANAGER" || reimbursementinfo.status != "PENDING")
    {
        document.getElementById("approve-reim").style.display = "none";
        document.getElementById("deny-btn").style.display = "none";
    }
    else
    {
        document.getElementById("approve-reim").style.display = "inline";
        document.getElementById("deny-btn").style.display = "inline";
    }
    document.getElementsByClassName("opacity2")[0].style.display = "inline";
}

document.getElementById("close-btn").addEventListener("click", async function(event)
{
    event.preventDefault();
    document.getElementsByClassName("opacity2")[0].style.display = "none";
    let reimFormContainer = document.getElementById("reim-info");
    reimFormContainer.removeChild(document.getElementById("reim-info-elem"));

    let creatorFormContainer = document.getElementById("creator-info");
    creatorFormContainer.removeChild(document.getElementById("creator-info-elem"));

    let resolverFormContainer = document.getElementById("resolver-info");
    resolverFormContainer.removeChild(document.getElementById("creator-info-elem"));
});

document.getElementById("approve-reim").addEventListener("click", async function(event)
{
    event.preventDefault();
    let response = await fetch(`${domain}/reimbursement/${reimId}/${'APPROVED'}/${user.id}`,{
        method: "PATCH"
    });

    let responseBody = await response.json();

    console.log(responseBody.data);

    window.location = `?userId=${user.id}&status=${Status}`
});

document.getElementById("deny-btn").addEventListener("click", async function(event)
{
    event.preventDefault();
    let response = await fetch(`${domain}/reimbursement/${reimId}/${'DENIED'}/${user.id}`,{
        method: "PATCH"
    });

    let responseBody = await response.json();

    console.log(responseBody.data);

    window.location = `?userId=${user.id}&status=${Status}`
});

