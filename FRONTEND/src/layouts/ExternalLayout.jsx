import Header from '../components/Header';
import Footer from '../components/Footer';

export default function Layout({ children }) {
	return (
		<div className="w-full h-full flex flex-col">
			<Header />
			<main className=" flex">{children}</main>
			<Footer />
		</div>
	);
}
