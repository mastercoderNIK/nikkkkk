import tkinter as tk
from tkinter import messagebox

# Hardcoded exchange rates (for example purposes)
exchange_rates = {
    "USD": {"INR": 83.0, "EUR": 0.94, "GBP": 0.82, "JPY": 149.6},
    "INR": {"USD": 0.012, "EUR": 0.011, "GBP": 0.0098, "JPY": 1.80},
    "EUR": {"USD": 1.06, "INR": 88.50, "GBP": 0.87, "JPY": 159.0},
    "GBP": {"USD": 1.22, "INR": 101.85, "EUR": 1.15, "JPY": 183.0},
    "JPY": {"USD": 0.0067, "INR": 0.55, "EUR": 0.0063, "GBP": 0.0055}
}

# Function to perform currency conversion
def convert_currency():
    try:
        amount = float(entry_amount.get())
        from_currency = from_var.get()
        to_currency = to_var.get()
        
        if from_currency == to_currency:
            result_label.config(text=f"{amount:.2f} {from_currency} = {amount:.2f} {to_currency}")
        else:
            conversion_rate = exchange_rates[from_currency][to_currency]
            converted_amount = amount * conversion_rate
            result_label.config(text=f"{amount:.2f} {from_currency} = {converted_amount:.2f} {to_currency}")
    except ValueError:
        messagebox.showerror("Invalid Input", "Please enter a valid number for the amount.")
    except KeyError:
        messagebox.showerror("Error", "Currency conversion not available for selected currencies.")

# Function to clear the fields
def clear_fields():
    entry_amount.delete(0, tk.END)
    result_label.config(text="")

# GUI setup
root = tk.Tk()
root.title("Currency Converter")
root.geometry("400x280")
root.configure(bg="white")

# Styling
font_large = ('Arial', 14, 'bold')
font_medium = ('Arial', 12)

# Title
title_label = tk.Label(root, text="Currency Converter", font=font_large, bg="white", fg="darkblue")
title_label.grid(row=0, column=1, pady=10)

# Amount Entry
amount_label = tk.Label(root, text="Amount:", font=font_medium, bg="white", fg="black")
amount_label.grid(row=1, column=0, padx=10, pady=10)
entry_amount = tk.Entry(root, font=font_medium)
entry_amount.grid(row=1, column=1, padx=10)

# From Currency Dropdown
from_label = tk.Label(root, text="From Currency:", font=font_medium, bg="white", fg="black")
from_label.grid(row=2, column=0, padx=10, pady=10)
from_var = tk.StringVar(root)
from_var.set("USD")  # Default value
from_menu = tk.OptionMenu(root, from_var, "USD", "INR", "EUR", "GBP", "JPY")
from_menu.grid(row=2, column=1, padx=10)

# To Currency Dropdown
to_label = tk.Label(root, text="To Currency:", font=font_medium, bg="white", fg="black")
to_label.grid(row=3, column=0, padx=10, pady=10)
to_var = tk.StringVar(root)
to_var.set("INR")  # Default value
to_menu = tk.OptionMenu(root, to_var, "USD", "INR", "EUR", "GBP", "JPY")
to_menu.grid(row=3, column=1, padx=10)

# Result Label (moved above the buttons)
result_label = tk.Label(root, text="", font=font_large, bg="white", fg="green")
result_label.grid(row=4, column=1, pady=15)

# Convert Button
convert_button = tk.Button(root, text="Convert", font=font_medium, command=convert_currency, bg="lightblue", fg="black")
convert_button.grid(row=5, column=0, pady=10)

# Clear Button (Fixed)
clear_button = tk.Button(root, text="Clear", font=font_medium, command=clear_fields, bg="lightcoral", fg="black")
clear_button.grid(row=5, column=2, pady=10)

root.mainloop()


