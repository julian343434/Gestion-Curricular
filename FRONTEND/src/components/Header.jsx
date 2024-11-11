import React from 'react';
import {
	Navbar,
	NavbarBrand,
	NavbarContent,
	NavbarItem,
	Link,
	Button,
} from '@nextui-org/react';

export default function Header() {
	return (
		<Navbar className="bg-[#c41e3ae3]" maxWidth="full">
			<NavbarBrand>
				<div className="flex items-center justify-center gap-4">
					<img src="./logo.png" className="w-12 h-13" />
					<span className="font-bold text-white text-lg">
						Universidad de los Llanos
					</span>
				</div>
			</NavbarBrand>

			<NavbarContent className="hidden sm:flex gap-4" justify="center">
				<NavbarItem>
					<Link href="#" className="text-white">
						Inicio
					</Link>
				</NavbarItem>
				<NavbarItem>
					<Link href="#" className="text-white">
						Facultades
					</Link>
				</NavbarItem>
				<NavbarItem>
					<Link href="#" className="text-white">
						Contacto
					</Link>
				</NavbarItem>
			</NavbarContent>

			<NavbarContent justify="end">
				<NavbarItem>
					<Button
						as={Link}
						href="/login"
						className="bg-white text-[#C41E3A] font-semibold"
					>
						Login
					</Button>
				</NavbarItem>
			</NavbarContent>
		</Navbar>
	);
}
