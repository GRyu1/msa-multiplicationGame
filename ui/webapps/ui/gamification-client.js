var SERVER_URL = "http://localhost:8000/api";

function updateLeaderBoard() {
  $.ajax({
    url: SERVER_URL + "/leaders"
  }).then(function (data) {
    $('#leaderboard-body').empty();
    data.forEach(function (row) {
      $('#leaderboard-body').append('<tr><td>' + row.playerId + '</td>' +
        '<td>' + row.totalScore + '</td>');
    });
  });
}

function updateStats(playerId) {
  $.ajax({
    url: SERVER_URL + "/stats?playerId=" + playerId,
    success: function (data) {
      $('#stats-div').show();
      $('#stats-user-id').empty().append(playerId);
      $('#stats-score').empty().append(data.score);
      $('#stats-badges').empty().append(data.badges.join());
    },
    error: function (data) {
      $('#stats-div').show();
      $('#stats-user-id').empty().append(playerId);
      $('#stats-score').empty().append(0);
      $('#stats-badges').empty();
    }
  });
}

$(document).ready(function () {

  updateLeaderBoard();

  $("#refresh-leaderboard").click(function (event) {
    updateLeaderBoard();
  });

});
