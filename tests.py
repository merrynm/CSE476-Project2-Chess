import requests
import time


BASE_URL = "https://webdev.cse.msu.edu/~schwagl3/cse476/project2/"
#LOGIN = "game-login.php"
LOGIN = "login_2.php"
NEW_USER = "new-user.php"
DELETE = "deleteChess.php"
#PULL = "pull.php"
PULL = "pull2.php"
GET_PLAYERS = "get_player.php"
#PUSH = "saveChess.php"
PUSH = "push2.php"
#END = "end.php"
END = "end2.php"

TIME = "push.php"


def test_time():
    payload = { "set" : 1}
    r = requests.get(BASE_URL + TIME)
    return r

def test_login_valid():
    xml = "<secretary username=\"bench\" password=\"mark\" />"
    r = requests.post(BASE_URL + LOGIN, data = { "xml": xml })
    return r

def test_login_invalid():
    xml = "<secretary username=\"tt\" password=\"ww\" />"
    r = requests.post(BASE_URL + LOGIN, data = { "xml": xml })
    return r

def test_new_user():
    xml = "<secretary username=\"use\" password=\"less\" />"
    r = requests.post(BASE_URL + NEW_USER, data = { "xml": xml })
    return r

def test_new_user_dynamic(username, password):
    xml = "<secretary username=\"" + username + "\" password=\""+ password + "\" />"
    r = requests.post(BASE_URL + NEW_USER, data = { "xml": xml })
    return r

def login_user_dynamic(username, password):
    xml = "<secretary username=\"" + username + "\" password=\""+ password + "\" />"
    r = requests.post(BASE_URL + LOGIN, data = { "xml": xml })
    return r

def test_pull(username, password):
    payload = { 'name' : username, 'password': password }
    r = requests.get(BASE_URL + PULL, params=payload)
    return r


def test_delete():
    r = requests.get(BASE_URL + DELETE)
    return r

def test_get_player_names(username, password):
    payload = { 'name' : username, 'password': password }
    r = requests.get(BASE_URL + GET_PLAYERS, params=payload)
    return r

def test_push(username, password):
    xml = "<secretary username=\"" + username + "\" password=\""+ password + "\" ><piece player_color=\"black\" piece_type=\"1\" board_row_x=\"0.000\" board_col_y=\"0.000\"/><piece player_color=\"black\" piece_type=\"1\" board_row_x=\"0.000\" board_col_y=\"0.000\"/></secretary>"
    r = requests.post(BASE_URL + PUSH, data = { "xml": xml })
    return r

data ="""<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><secretary username="perry" password="wilson"><piece player_color="black" piece_type="ROOK" board_row_x="0.06" board_col_y="0.06" /><piece player_color="black" piece_type="KNIGHT" board_row_x="0.186" board_col_y="0.06" /><piece player_color="black" piece_type="BISHOP" board_row_x="0.311" board_col_y="0.06" /><piece player_color="black" piece_type="QUEEN" board_row_x="0.437" board_col_y="0.06" /><piece player_color="black" piece_type="KING" board_row_x="0.562" board_col_y="0.06" /><piece player_color="black" piece_type="BISHOP" board_row_x="0.688" board_col_y="0.06" /><piece player_color="black" piece_type="KNIGHT" board_row_x="0.813" board_col_y="0.06" /><piece player_color="black" piece_type="ROOK" board_row_x="0.939" board_col_y="0.06" /><piece player_color="black" piece_type="PAWN" board_row_x="0.06" board_col_y="0.186" /><piece player_color="black" piece_type="PAWN" board_row_x="0.186" board_col_y="0.186" /><piece player_color="black" piece_type="PAWN" board_row_x="0.311" board_col_y="0.186" /><piece player_color="black" piece_type="PAWN" board_row_x="0.437" board_col_y="0.186" /><piece player_color="black" piece_type="PAWN" board_row_x="0.562" board_col_y="0.186" /><piece player_color="black" piece_type="PAWN" board_row_x="0.688" board_col_y="0.186" /><piece player_color="black" piece_type="PAWN" board_row_x="0.813" board_col_y="0.186" /><piece player_color="black" piece_type="PAWN" board_row_x="0.939" board_col_y="0.186" /><piece player_color="white" piece_type="ROOK" board_row_x="0.06" board_col_y="0.939" /><piece player_color="white" piece_type="KNIGHT" board_row_x="0.186" board_col_y="0.939" /><piece player_color="white" piece_type="BISHOP" board_row_x="0.311" board_col_y="0.939" /><piece player_color="white" piece_type="QUEEN" board_row_x="0.437" board_col_y="0.939" /><piece player_color="white" piece_type="KING" board_row_x="0.562" board_col_y="0.939" /><piece player_color="white" piece_type="BISHOP" board_row_x="0.688" board_col_y="0.939" /><piece player_color="white" piece_type="KNIGHT" board_row_x="0.813" board_col_y="0.939" /><piece player_color="white" piece_type="ROOK" board_row_x="0.939" board_col_y="0.939" /><piece player_color="white" piece_type="PAWN" board_row_x="0.06" board_col_y="0.813" /><piece player_color="white" piece_type="PAWN" board_row_x="0.186" board_col_y="0.813" /><piece player_color="white" piece_type="PAWN" board_row_x="0.437" board_col_y="0.813" /><piece player_color="white" piece_type="PAWN" board_row_x="0.562" board_col_y="0.813" /><piece player_color="white" piece_type="PAWN" board_row_x="0.688" board_col_y="0.813" /><piece player_color="white" piece_type="PAWN" board_row_x="0.813" board_col_y="0.813" /><piece player_color="white" piece_type="PAWN" board_row_x="0.939" board_col_y="0.813" /><piece player_color="white" piece_type="PAWN" board_row_x="0.311" board_col_y="0.562" /></secretary>"""

def test_push(username, password):
    xml = "<secretary username=\"" + username + "\" password=\""+ password + "\" ><piece player_color=\"black\" piece_type=\"1\" board_row_x=\"0.000\" board_col_y=\"0.000\"/><piece player_color=\"black\" piece_type=\"1\" board_row_x=\"0.000\" board_col_y=\"0.000\"/></secretary>"
    r = requests.post(BASE_URL + PUSH, data = { "xml": data })
    return r

def test_end(username, password):
    payload = { 'name' : username, 'password': password }
    r = requests.get(BASE_URL + END, params=payload)
    return r




tests = [test_login_valid, test_login_invalid, test_new_user]

if __name__ == '__main__':
    # for i in tests:
    #     print("RUNNING TEST")
    #     print(i().text)
    #     print("TEST COMPLETE")
    #while True:
    #    print("Current time: %s" %  test_time().text)
    print(test_delete().text)
    exit()

    print(test_new_user_dynamic("Madison", "binself").text)
    print(login_user_dynamic("Madison", "binself").text)
    print(test_pull("Madison", "binself").text)

    print(test_new_user_dynamic("perry", "wilson").text)
    print(login_user_dynamic("perry", "wilson").text)
    print(test_pull("perry", "wilson").text)

    print(test_push("Madison", "binself").text)
    print("MADISON PUSHED")
    #time.sleep(5)

    #counter = 0
    #while counter < 0:
    #    time.sleep(1)
    #    print(test_pull("Madison", "binself").text)
    #    counter += 1

    #print(test_push("perry", "wilson").text)
    #time.sleep(2)

    counter = 0;
    while counter < 17:
        time.sleep(1)
        print("MADISON PULL")
        print(test_pull("Madison", "binself").text)
        counter += 1
    print(test_push("perry", "wilson").text)
    print("PERRY PUSHED")

    while True:
        time.sleep(1)
        print("PERRY PULL")
        print(test_pull("perry", "wilson").text)



    #print(test_new_user_dynamic("m", "m").text)
    #print(login_user_dynamic("m", "m").text)
    #print(test_pull("m", "m").text)

    #print(test_end("Madison", "binself").text)
    #print(test_pull("perry", "wilson").text);

   #print(test_pull("Madison", "binself").text)
    #print(test_pull("perry", "wilson").text)

    #print(test_push("Madison", "binself").text)
    #print(test_pull("Madison", "binself").text)
    #print(test_pull("perry", "wilson").text)
