package com.synergisticit.AirlinesReservation.dao.passenger;

import com.synergisticit.AirlinesReservation.domain.Passenger;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository("passenger")
@Transactional(readOnly = true, propagation= Propagation.NOT_SUPPORTED)

public class PassengerDaoImpl implements PassengerDao {

    SessionFactory sessionFactory;
    public PassengerDaoImpl(EntityManagerFactory entityManagerFactory) {
        if(entityManagerFactory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("EntityManagerFactory is null");
        }
        this.sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
    }


    @Override
    public Passenger savePassenger(Passenger passenger) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(passenger);
            session.getTransaction().commit();
            session.close();
            return passenger;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Passenger> savePassengers(List<Passenger> passengers) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            for(Passenger passenger : passengers) {
                session.persist(passenger);
            }
            session.getTransaction().commit();
            return passengers;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Passenger> getAllPassengers() {
        List<Passenger> branches = null;
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            TypedQuery<Passenger> query = session.createQuery("from passenger");
            branches = query.getResultList();
            session.getTransaction().commit();
            return branches;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Passenger getPassengerById(Long id) {
        Passenger branch = null;
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            branch = (Passenger) session.get(Passenger.class, id);
            session.getTransaction().commit();
            return branch;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Passenger updatePassenger(Passenger passenger) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(passenger);
            session.getTransaction().commit();
            return passenger;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deletePassenger(Long id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Passenger branch = (Passenger) session.get(Passenger.class, id);
            if(branch != null) {
                session.remove(branch);
            } else {
                System.out.println("Passenger not found");
            }
            session.getTransaction().commit();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Page<Passenger> findAll(Pageable pageable) {
        String numRows = "select count(*) from passenger";
        String strNew = String.valueOf(pageable.getSort()).replace(":", "");
        int pageSize = pageable.getPageSize();

        List<Passenger> passengers = null;
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String sql = "from passenger p order by p." + strNew;
            Query query = sessionFactory.getCurrentSession().createQuery(sql, Passenger.class);
            query.setFirstResult(pageable.getPageNumber());
            query.setMaxResults(pageSize);
            passengers = (List<Passenger>) query.list();
            Query numRowQuery = sessionFactory.getCurrentSession().createQuery(numRows);
            long totalRows = (long) numRowQuery.uniqueResult();
            session.getTransaction().commit();
            return new PageImpl<Passenger>(passengers, pageable, totalRows);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Long getLastPassengerId() {
        String sql = "SELECT passengerId FROM passenger ORDER BY passengerId DESC LIMIT 1";
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = sessionFactory.getCurrentSession().createQuery(sql);
            Long id = (Long) query.uniqueResult();
            session.getTransaction().commit();
            return id;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Passenger findPassengerByEmail(String email) {
        Passenger passenger = null;
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            passenger = (Passenger) session.createQuery("select p from passenger p where p.email=:email").setParameter("email", email).setMaxResults(1).uniqueResult(); //session.get(Passenger.class, email);
            session.getTransaction().commit();
            return passenger;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Passenger findPassengerByIdAndFirstName(String firstName, String idNumber) {
        Passenger passenger = null;
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            passenger = (Passenger) session.createQuery("select p from passenger p where p.firstName=:firstName and p.idNumber=:idNumber").setParameter("firstName", firstName).setParameter("idNumber", idNumber).setMaxResults(1).uniqueResult();
            session.getTransaction().commit();
            return passenger;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Passenger findPassengerByIdNumber(String id) {
        Passenger passenger = null;
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            passenger = (Passenger) session.createQuery("select p from passenger p where p.idNumber=:id").setParameter("id", id).setMaxResults(1).uniqueResult(); //session.get(Passenger.class, email);
            session.getTransaction().commit();
            return passenger;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
