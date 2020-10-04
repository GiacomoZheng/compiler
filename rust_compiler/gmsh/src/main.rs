// use std::env;
// use std::process;

use std::io;

fn main() {
    let mut line = String::new();
	
	loop {
        print!("> ");
        
		line = String::new();
		io::stdin().read_line(&mut line)
			.expect("Failed to read line");
		println!("{}", line);
    }
    
    let mut input = String::new();

    io::stdin().read_line(&mut input).expect("Failed to read line");

    println!("You typed: {}", input.trim());
}
