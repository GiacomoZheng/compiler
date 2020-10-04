use std::collections::HashSet;

// C0: no comments / strings / matching / subscripts appear
pub fn lexer(name : &'static str, text : String) -> Vec<String> {
	let flag = text.starts_with(":");

	let mut vec : Vec<String> = Vec::new();
	vec.push(name.to_owned());

	let mut iter = text.chars();

	if !flag {
		vec.push(":".to_owned());
		vec.push("(".to_owned());
	}

	let sep : HashSet<_> = [':', ',', '(', ')', '.', '∀', '∃'].iter().collect();
	let blk : HashSet<_> = [' ', '\t', '\n'].iter().collect();

	let mut tmp = String::new();
	while let Some(e) = iter.next() {
		if sep.contains(&e) {
			if !tmp.is_empty() {
				vec.push(tmp.clone());
				tmp.clear();
			}

			vec.push(e.to_string());
		} else if blk.contains(&e) {
			if !tmp.is_empty() {
				vec.push(tmp.clone());
				tmp.clear();
			}
		} else {
			tmp.push(e);
		}
	}

	if !flag {
		vec.push(")".to_owned());
		vec.push(",".to_owned());
	}

	vec
}

trait Node {
	fn to_sh(&self) -> String;

}

pub struct AST {
	root : Box<dyn Node>,

}

impl AST {
	pub fn to_sh(&self) -> String {
		self.root.to_sh()
	}
}


pub fn parsing(v : Vec<String>) -> AST {

	unimplemented!()
}

#[cfg(test)]
mod test {
	use super::*;
	use std::fs::File;
	use std::io::prelude::*;
	#[test]
	fn test() -> std::io::Result<()> {
		// let s = String::from(": ( ∈ ll, pass = 1),");
		let mut s = String::new();
		File::open("./res/topological_space.gm")?.read_to_string(&mut s)?;
		println!("{:?}", lexer("topological_space", s));
		Ok(())
	}
}

