from bs4 import BeautifulSoup
import sqlite3
import requests
import config

conn = sqlite3.connect("data.db")


def append(a: list):
    cursor = conn.cursor()

    def __do(t: tuple):
        cursor.execute('''
                    INSERT INTO datatable (name,year,month,day,count)
                    VALUES (?,?,?,?,?)
                    ''', t)

    return map(__do, map(lambda x: (x["name"], x["year"], x["month"], x["day"], x["count"]), a))


def delete_all(commit=True):
    conn.execute("delete from datatable")
    if commit:
        conn.commit()


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

    return append(list(map(__set_name, map(setNote, map(lambda x: x.attrs, BeautifulSoup(
        requests.get("https://github.com/" + name, params={
            'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36',
        }).text, 'lxml').find_all("rect"))))))


def parser():
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
    f = map(main, config.person)

    def __it(f):
        try:
            while True:
                line = next(f)
                if type(line) == map:
                    __it(line)
        except StopIteration:
            pass

    __it(f)
    conn.commit()


def count_month(name, year, month):
    for i in conn.execute("select distinct count(*) from datatable where name=? and year=? and month=? and count>0",
                          (name, year, month)):
        return i[0]


def count_year(name, year):
    for i in conn.execute("select distinct count(*) from datatable where name=? and year=? and count>0", (name, year)):
        return i[0]


def save_to_file(file_name, data: list):
    with open(file=file_name,mode="w") as file:
        to_write = ["Name\t\tYear\tMonth\tCount\n",]
        for person in data:
            for date in person:
                to_write.append(date[0]+"\t"+date[1]+"\t"+date[2]+"\t"+date[3]+"\n")
        write = ""
        for i in to_write:
            write += i
        file.write(write)
        if config.print_on_cmd:
            print(write)



if __name__ == '__main__':
    if config.parser:
        delete_all(False)
        parser()
        conn.commit()
    data = []
    for name in config.person:
        person = []
        for date in config.date_time:
            if len(date) == 2:
                out = count_month(str(name), str(date[0]), str(date[1]))
                person.append([str(name), str(date[0]), str(date[1]), str(out)])
            else:
                out = count_year(str(name), str(date[0]))
                person.append([str(name), str(date[0]), "None", str(out)])
        data.append(person)
    if config.save_on_the_file:
        save_to_file(config.file, data)

    conn.close()
