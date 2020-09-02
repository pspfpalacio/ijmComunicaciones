package ar.com.clases;

import java.io.Serializable;
import java.util.List;

import model.Caja;
import DAO.caja.DAOcaja;

public class Cajas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DAOcaja cajaDAO = new DAOcaja();

	public DAOcaja getCajaDAO() {
		return cajaDAO;
	}

	public void setCajaDAO(DAOcaja cajaDAO) {
		this.cajaDAO = cajaDAO;
	}
	
//	public int insertarCaja(Caja movimiento){
//		try{
//			List<Caja> lista = cajaDAO.getListadoCajaOrdenadoParaControl();
//			boolean empty = lista.isEmpty();
//			float monto = movimiento.getMonto();
//			if (movimiento.getTipo().equals("Salida")) {
//				monto = monto * (-1);
//			}
//			if (empty) {
//				float total = movimiento.getTotal();
//				total = total + monto;
//				movimiento.setTotal(total);
//				int inserto = cajaDAO.insertCaja(movimiento);
//				if (inserto == 0) {
//					return 0;
//				} else {
//					return 1;
//				}
//			} else {
//				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
//				List<Caja> listPosterior = new ArrayList<Caja>();
//				Caja cajaAnterior = new Caja();
//				for (Caja caja : lista) {
//					String inicio = formato.format(caja.getFecha());
//					Date fechaCuenta2 = formato.parse(inicio);
//					fechaCuenta2.setHours(0);
//					fechaCuenta2.setMinutes(0);
//					fechaCuenta2.setSeconds(0);
//					int comparaFecha = fechaCuenta2.compareTo(movimiento.getFecha());
//					if(comparaFecha > 0){
//						listPosterior.add(caja);
//					}else{
//						cajaAnterior = new Caja();
//						cajaAnterior = caja;
//					}
//				}
//			}
//			
//			
//			
//		}catch(Exception e){
//			System.out.println(e.getMessage());
//			return 0;
//		}
//	}
//	
//	public int deleteCaja(Caja caja){
//		try{
//			Date fechaHasta = new Date();	
//			Caja caja2 = cajaDAO.get(caja.getIdMovimiento(), caja.getNombreTabla());
//			float monto = (-1) * caja2.getMonto();
//			List<Caja> listAux = cajaDAO.getListaOrdenadaParaInsercion(caja2.getFecha(), fechaHasta);
//			List<Caja> listAux2 = new ArrayList<Caja>();
//			boolean update = true;
//			boolean pasoRegistro = false;
//			for (Caja caja3 : listAux) {
//				if(caja2.getId() != caja3.getId()){
//					if(pasoRegistro){
//						listAux2.add(caja3);
//					}
//				}else{
//					pasoRegistro = true;
//				}
//			}
//			for (Caja caja3 : listAux2) {
//				float saldo = caja3.getSaldo() + monto;
//				caja3.setSaldo(saldo);
//				if(cajaDAO.update(caja3) == 0){
//					update = false;
//				}
//			}
//			if(update){
//				if(cajaDAO.delete(caja2) != 0){
//					return 1;
//				}else{
//					return 0;
//				}
//			}else{
//				return 0;
//			}
//		}catch(Exception e){
//			System.out.println(e.getMessage());
//			return 0;
//		}
//	}
//	
//	public int upgradeDeleteCaja(Caja caja){
//		try{
//			Date fechaHasta = new Date();	
//			Caja caja2 = cajaDAO.get(caja.getIdMovimiento(), caja.getNombreTabla());
//			float monto = (-1) * caja2.getMonto();
//			List<Caja> listAux = cajaDAO.getListaOrdenadaParaInsercion(caja2.getFecha(), fechaHasta);
//			boolean update = true;
//			for (Caja caja3 : listAux) {
//				float saldo = caja3.getSaldo() + monto;
//				caja3.setSaldo(saldo);
//				if(cajaDAO.update(caja3) == 0){
//					update = false;
//				}
//			}
//			if(update){
//				if(cajaDAO.delete(caja2) != 0){
//					return 1;
//				}else{
//					return 0;
//				}
//			}else{
//				return 0;
//			}
//		}catch(Exception e){
//			System.out.println(e.getMessage());
//			return 0;
//		}
//	}
	
	public int actualizaCaja() {
		List<Caja> listaCajas = cajaDAO.getListadoCajaOrdenadoParaControl();
		int retorno = 1;
		float monto = 0;
		for (Caja caja : listaCajas) {
			float m = caja.getMonto();
			if (caja.getTipo().equals("Salida")) {
				m = m * (-1);
			}
			monto = monto + m;
			caja.setTotal(monto);
			int update = cajaDAO.update(caja);
			if (update == 0) {
				retorno = 0;
			}
		}
		return retorno;
	}

}
