import React from 'react'
import { Link } from "@nextui-org/react"

export default function Footer() {
  return (
    <footer className="bg-[#c41e3ae3] text-white py-8">
      <div className="container mx-auto px-4">
        <div className="grid grid-cols-1 md:grid-cols-4 gap-8">
          <div>
            <h3 className="font-bold text-lg mb-4">Contacto</h3>
            <p>Email: info@unillanos.edu</p>
            <p>Teléfono: (123) 456-7890</p>
          </div>
          <div>
            <h3 className="font-bold text-lg mb-4">Enlaces Rápidos</h3>
            <ul className="space-y-2">
              <li><Link href="#" className="text-white hover:underline">Carreras</Link></li>
              <li><Link href="#" className="text-white hover:underline">Calendario</Link></li>
            </ul>
          </div>
          <div>
            <h3 className="font-bold text-lg mb-4">Redes Sociales</h3>
            <ul className="space-y-2">
              <li><Link href="#" className="text-white hover:underline">Facebook</Link></li>
              <li><Link href="#" className="text-white hover:underline">Twitter</Link></li>
              <li><Link href="#" className="text-white hover:underline">Instagram</Link></li>
            </ul>
          </div>
          <div>
            <h3 className="font-bold text-lg mb-4">Ubicación</h3>
            <p>Campus Barcelona: Km. 12 vía Puerto López</p>
            <p>Campus San Antonio: Calle 37 No. 41-02 Barzal</p>
            <p>Campus Boquemonte: KM 2 Vía San Juan de Arama - Granada, Meta</p>
            <p>Edificio Emporio: Calle 40 A No. 28-32 Emporio</p>
          </div>
        </div>
        <div className="mt-8 text-center">
          <p>© 2024 Universidad de los Llanos. Todos los derechos reservados.</p>
        </div>
      </div>
    </footer>
  )
}