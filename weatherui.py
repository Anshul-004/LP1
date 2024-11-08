import tkinter as tk
from tkinter import messagebox


def on_search():
    city = entry_city.get()
    if city == "nashik":

        simulated_result = f"The Weather In {city} Is :\nMostly Cloudy, 27°C"
        text_result.config(state=tk.NORMAL)
        text_result.delete(1.0, tk.END)
        text_result.insert(tk.END, simulated_result)
        text_result.config(state=tk.DISABLED)
    else:
        simulated_result = f" "
        text_result.config(state=tk.NORMAL)
        text_result.delete(1.0, tk.END)
        text_result.insert(tk.END, simulated_result)
        text_result.config(state=tk.DISABLED)
        messagebox.showwarning("Input Error", "Please enter a city name.")


root = tk.Tk()
root.title("Weather App")
root.configure(bg='slate gray')
font_settings = ('Anton SC', 12, 'bold')
label_city = tk.Label(root, text="Show Me The Weather For : ", bg='slate gray', fg='white',
                      font=font_settings)
label_city.pack(padx=10, pady=5)
entry_city = tk.Entry(root, width=40, font=font_settings)
entry_city.pack(padx=10, pady=5)
button_search = tk.Button(root, text="Get Weather", command=on_search, bg='slate gray',
                          fg='white', font=font_settings)
button_search.pack(padx=10, pady=5)
text_result = tk.Text(root, height=10, width=50, wrap=tk.WORD, bg='slate gray', fg='white',
                      font=font_settings)
text_result.pack(padx=10, pady=5)
text_result.config(state=tk.DISABLED)
root.mainloop()
