mod lex;
mod class;

use std::rc::{Rc, Weak};
// use std::fs::File;

use std::collections::HashMap;
fn analyze(text : String) -> HashMap<String, Rc<Location>> {
	if text.starts_with(":") {
		// case 1: start with `:`
		
	}
	unimplemented!()
}

pub struct Location {
	subs : HashMap<String, Rc<Location>>,
	parent : Weak<Location>,

	name : &'static str,
	path : Path,
}
impl PartialEq for Location {
	fn eq(&self, other: &Self) -> bool {
		self.name == other.name && self.parent.upgrade() == other.parent.upgrade()
    }
}

impl Location {
	pub fn from(text : String) -> Location {
		Location {
			subs : analyze(text),
			parent : Weak::new(),
			
			name : "gm",
			path : Path::from("gm"),
		}
	}

	pub fn cd(self, subname : String) -> Result<Rc<Location>, &'static str> {
		if let Some(e) = self.subs.get(&subname) {
			Ok(Rc::clone(e))
		} else {
			Err("no such a sub location")
		}
	}
}

#[derive(Debug, PartialEq)]
pub struct Path(Vec<&'static str>);
impl Path {
	pub fn from(path_string : &'static str) -> Path {
		let mut vec : Vec<&'static str> = path_string.split(".").collect();
		if vec.is_empty() {
			panic!("a path should be nonempty")
		} else if vec.first() == Some(&"") {
			vec[0] = "gm";
		}

		if vec.iter().all(|x| !x.is_empty()) {
			Path(vec)
		} else {
			panic!("pattern \"..\" are not allowed in the path")
		}
	}

	pub fn is_absolute(&self) -> bool {
		self.0.first() == Some(& "gm")
	}
	pub fn as_absolute(&self) -> Path {
		unimplemented!()
	}

	pub fn is_relative(&self) -> bool {
        !self.is_absolute()
	}
	pub fn as_relative(&self) -> Path {
		unimplemented!()
	}
	
	pub fn name(&self) -> String {
		unimplemented!()
	}
	
	pub fn starts_with(&self, base: Path) -> bool {
		self.0.starts_with(&base.0)
	}
}
use std::fmt;
impl fmt::Display for Path {
	fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
		let mut iter = self.0.iter();
		if self.is_absolute() {
			iter.next();
			write!(f, "gm")?
		} else {
			write!(f, ".")?
		}
		write!(f, "{}", iter.fold(String::new(), |acc, x| format!("{}.{}", acc, x)))
    }
}
#[test]
fn path() {
	let path = Path::from("a.s.f");
	assert_eq!(path.0.first(), Some(&"a"));
	assert!(path.is_relative());
	assert_eq!(format!("{}", path), "..a.s.f");
	
	let path = Path::from(".s.f");
	assert_eq!(path.0.first(), Some(&"gm"));
	assert!(path.is_absolute());
	assert_eq!(format!("{}", path), "gm.s.f");
}


use std::collections::HashSet;
struct Identifier {}

pub struct Shell {
	cwd : Location, // ?
	current_delimiters : HashSet<Identifier>,
	system_delimiters : HashSet<Identifier>,

}

#[cfg(test)]
mod tests;