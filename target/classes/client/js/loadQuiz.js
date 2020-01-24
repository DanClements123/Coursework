function pageLoad(){
    loadQuiz();
}

function loadQuiz() {
    let QuestionsHTML = `<table>` +
        `<tr>` +
        `<th>Quiz Name</th>`+
        `<th>Question ID</th>`
        `</tr>`;
    console.log(QuestionsHTML);
    fetch('/Questions/quizName', {method: 'get'}
    ).then(response => response.json()
    ).then(Questions => {

        for (let quizName of Questions) {
            let i = 0;
            let random = 0;
            while (i <= 10) {
                random = Math.floor((Math.random() * Questions.size) + 1);
                i++;
            }
            QuestionsHTML += `<tr>` +
                `<td>${quizName.quizName}</td>` +
                `<button class='playButton' data-id='${questionID.id}'>Play Quiz</button>` +
                `</td>` +
                `<section id="answerPosition"><input type="button" style="height:50px; width:100px" onclick="location.href='http://localhost:8081/client/playQuiz.html';" value="Answer 1" /></section>`+
                `<section id="answerPosition2"><input type="button" style="height:50px; width:100px" onclick="location.href='http://localhost:8081/client/login.html';" value="Answer 2" /></section>`+
                `</tr>`;
        }

        QuestionsHTML += '</table>';
        document.getElementById("listDiv").innerHTML = QuestionsHTML;
        let editButtons = document.getElementsByClassName("playButton");
        for (let button of editButtons) {
            button.addEventListener("click", playButton);
        }
    })

}//end of function
