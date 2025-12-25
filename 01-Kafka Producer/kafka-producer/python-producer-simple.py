import random
import json
import time

sim_time = time.time()
SIM_STEP = 5


def gen_data():
    global sim_time
    sim_time+=SIM_STEP

    data = {
        "timestamp": sim_time,
        "power_kw": round(random.uniform(80,100),2)
    }

    print(json.dumps(data))

if __name__ == "__main__":
    while True:
        gen_data()
        time.sleep(0.01)