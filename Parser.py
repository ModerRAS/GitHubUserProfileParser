from bs4 import BeautifulSoup, Tag
import sqlite3
import requests
import re
import config

conn = sqlite3.connect("data.db")


def append(a: list):
    cursor = conn.cursor()
    def __do(t:tuple):
        cursor.execute('''
                    INSERT INTO datatable (name,year,month,day,count)
                    VALUES (?,?,?,?,?)
                    ''', t)

    return map(__do,map(lambda x: (x["name"], x["year"], x["month"], x["day"], x["count"]), a))



def setNote(data: dict):
    def _setNote(string: str):
        ret = {"count": int(string)}

        def getDate(x: str):
            for idi, i in enumerate(map(int, x.split("-"))):
                if idi == 0:
                    ret["year"] = i
                if idi == 1:
                    ret["month"] = i
                if idi == 2:
                    ret["day"] = i
            return ret

        return getDate

    return _setNote(data["data-count"])(data["data-date"])


def main(name: str):
    def __set_name(data: dict):
        data["name"] = name
        return data
    return append(list(map(__set_name, map(setNote, map(lambda x:x.attrs, BeautifulSoup(requests.get("https://github.com/" + name).text, 'lxml').find_all("rect"))))))



if __name__ == '__main__':
    try:
        conn.execute('''CREATE TABLE datatable(
                        name TEXT NOT NULL,
                        year INTEGER NOT NULL,
                        month INTEGER NOT NULL,
                        day INTEGER NOT NULL,
                        count INTEGER NOT NULL
                        )''')
    except sqlite3.OperationalError:
        pass
    # PRIMARY KEY(name)
    f = map(main,config.person)
    def __it(f):
        try:
            while True:
                line = next(f)
                print(line, end='')
                if type(line) == map:
                    __it(line)
        except StopIteration:
            pass
    __it(f)
    conn.commit()